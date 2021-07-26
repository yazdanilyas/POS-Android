package com.cybereast.p003spos_android.ui.fragments.updateStockFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseInterface
import com.cybereast.p003spos_android.base.BaseValidationFragment
import com.cybereast.p003spos_android.constants.Constants
import com.cybereast.p003spos_android.data.enums.DataMode
import com.cybereast.p003spos_android.databinding.UpdateStockFragmentBinding
import com.cybereast.p003spos_android.models.ProductModel
import com.cybereast.p003spos_android.utils.CommonKeys.KEY_DATA

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
        }
    }

    override fun onValidationSuccess() {
        updateProduct()
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
                mBinding.etProductQuantity,
            )
        }
        mBinding.etProductQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val qty = Integer.parseInt(str.toString())
                val salePrice = mViewModel.productModel?.productSalePrice
                if (salePrice != null) {
                    val totalAmount = salePrice.let { qty.times(it) }
                    mBinding.tvProductTotalAmount.text =
                        getString(R.string.total_amount) + totalAmount
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }


    private fun updateProduct() {
        onProgress()
        val productId = mViewModel.productModel?.productId.toString()
        val userUId = mViewModel.productModel?.userUId.toString()
        val oldQty = mViewModel.productModel?.productQuantity
        val productQuantity = mBinding.etProductQuantity.text.toString().toInt()
        val mRef = mFireStoreDbRef.collection(Constants.NODE_PRODUCTS).document(productId)
        val newQty = oldQty?.plus(productQuantity)
        val docData = mapOf<String, Int?>(
            "productQuantity" to newQty
        )

        mRef.update(docData).addOnSuccessListener {
            Log.d(TAG, getString(R.string.doc_successfully_written))
            requireActivity().finish()
            onResponse()
            requireActivity().finish()
        }.addOnFailureListener { e ->
            Log.w(TAG, getString(R.string.error_writing_doc), e)
            onResponse()
        }
    }


}