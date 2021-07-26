package com.cybereast.p003spos_android.ui.fragments.updateStockFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseInterface
import com.cybereast.p003spos_android.base.BaseValidationFragment
import com.cybereast.p003spos_android.constants.Constants
import com.cybereast.p003spos_android.data.enums.DataMode
import com.cybereast.p003spos_android.data.enums.TransactionType
import com.cybereast.p003spos_android.databinding.UpdateStockFragmentBinding
import com.cybereast.p003spos_android.models.LedgerModel
import com.cybereast.p003spos_android.models.ProductModel
import com.cybereast.p003spos_android.utils.CommonKeys.KEY_DATA
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

class UpdateStockFragment : BaseValidationFragment(), BaseInterface {

    companion object {
        fun newInstance() = UpdateStockFragment()
    }

    private lateinit var mViewModel: UpdateStockViewModel
    private lateinit var mBinding: UpdateStockFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = UpdateStockFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(UpdateStockViewModel::class.java)
        loadDataFromBundle()
        setListeners()

    }

    override fun onValidationError(editText: EditText) {
        when (editText.id) {
            R.id.etProductName -> {
                mBinding.etProductQuantity.error = getString(R.string.require_field)
            }
            R.id.etProductSalePrice -> {
                mBinding.etProductSalePrice.error = getString(R.string.require_field)
            }
        }
    }

    override fun onValidationSuccess() {
        writeStockAndLedgerEntry()
    }

    override fun onProgress() {
        mBinding.progressBar.visibility = View.VISIBLE
        mBinding.btnAddEditProduct.isEnabled = false
    }

    override fun onResponse() {
        mBinding.progressBar.visibility = View.GONE
        mBinding.btnAddEditProduct.isEnabled = true
    }


    private fun loadDataFromBundle() {
        if (arguments?.isEmpty != true) {
            mViewModel.productModel = arguments?.getSerializable(KEY_DATA) as ProductModel
            mViewModel.mode = DataMode.UPDATE.toString()
        }
        setViews()
    }

    private fun setViews() {
        mBinding.tvProductName.text = mViewModel.productModel?.productName
        mBinding.etProductSalePrice.setText(mViewModel.productModel?.productSalePrice.toString())
    }

    private fun setListeners() {

        mBinding.btnAddEditProduct.setOnClickListener {
            validateTextField(
                mBinding.etProductQuantity, mBinding.etProductSalePrice
            )
        }
        mBinding.etProductQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (str != null) {
                    if (str.isNotEmpty()) {
                        val qty = Integer.parseInt(str.toString())
                        val salePrice = mViewModel.productModel?.productSalePrice
                        if (salePrice != null) {
                            val totalAmount = salePrice.let { qty.times(it) }
                            mBinding.tvProductTotalAmount.text =
                                getString(R.string.total_amount) + totalAmount
                            mViewModel.totalAmount = totalAmount.toInt()
                        }
                    } else {
                        mBinding.tvProductTotalAmount.text =
                            getString(R.string.total_amount)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun writeStockAndLedgerEntry() {
        onProgress()
        // stock
        val productId = mViewModel.productModel?.productId.toString()
        val userUId = mViewModel.productModel?.userUId.toString()
        val oldQty = mViewModel.productModel?.productQuantity
        val productQuantity = mBinding.etProductQuantity.text.toString().toInt()
        val mRef = mFireStoreDbRef.collection(Constants.NODE_PRODUCTS).document(productId)
        val newQty = oldQty?.plus(productQuantity)
        val docData = mapOf<String, Int?>(
            "productQuantity" to newQty
        )
        // ledger
        val ledgerRef = mFireStoreDbRef.collection(Constants.NODE_LEDGER).document()
        val id = ledgerRef.id
        val date = Timestamp.now().seconds.toString().toLong()
        val ledger = LedgerModel(
            id,
            date,
            TransactionType.PURCHASE.toString(),
            0,
            mViewModel.totalAmount,
            FirebaseAuth.getInstance().uid
        )

        if (mViewModel.totalAmount ?: 0 > 0) {
            mFireStoreDbRef.runBatch {
                ledgerRef.set(ledger)
                mRef.update(docData)

                    .addOnSuccessListener {
                        Log.d(
                            BaseValidationFragment.TAG,
                            getString(R.string.doc_successfully_written)
                        )
                        onResponse()
                        requireActivity().finish()
                    }
                    .addOnFailureListener {
                        Log.w(BaseValidationFragment.TAG, getString(R.string.error_writing_doc), it)
                        onResponse()
                    }

            }
        } else {
            onResponse()
            Toast.makeText(
                requireContext(),
                getString(R.string.quantity_message),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}