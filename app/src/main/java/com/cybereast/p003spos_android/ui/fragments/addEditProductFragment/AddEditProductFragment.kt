package com.cybereast.p003spos_android.ui.fragments.addEditProductFragment

import android.os.Bundle
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
import com.cybereast.p003spos_android.constants.Constants.NODE_PRODUCT_DETAIL
import com.cybereast.p003spos_android.constants.Constants.NODE_PRODUCT_ID
import com.cybereast.p003spos_android.constants.Constants.NODE_PRODUCT_NAME
import com.cybereast.p003spos_android.constants.Constants.NODE_PRODUCT_PURCHASE_PRICE
import com.cybereast.p003spos_android.constants.Constants.NODE_PRODUCT_QUANTITY
import com.cybereast.p003spos_android.constants.Constants.NODE_PRODUCT_SALE_PRICE
import com.cybereast.p003spos_android.constants.Constants.NODE_USER_ID
import com.cybereast.p003spos_android.data.enums.DataMode
import com.cybereast.p003spos_android.databinding.AddEditProductFragmentBinding
import com.cybereast.p003spos_android.models.ProductModel
import com.cybereast.p003spos_android.utils.CommonKeys.KEY_DATA
import com.google.firebase.auth.FirebaseAuth

class AddEditProductFragment : BaseValidationFragment(), BaseInterface {

    companion object {
        fun newInstance() = AddEditProductFragment()
    }

    private lateinit var mViewModel: AddEditProductViewModel
    private lateinit var mBinding: AddEditProductFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = AddEditProductFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(AddEditProductViewModel::class.java)
        loadDataFromBundle()
        intiView()
        setListeners()
    }

    override fun onValidationError(editText: EditText) {
        when (editText.id) {
            R.id.etProductName -> {
                mBinding.etProductName.error = getString(R.string.require_field)
            }
            R.id.etProductPurchasePrice -> {
                mBinding.etProductPurchasePrice.error = getString(R.string.require_field)
            }
            R.id.etProductSalePrice -> {
                mBinding.etProductSalePrice.error = getString(R.string.require_field)
            }
            R.id.etProductQuantity -> {
                mBinding.etProductQuantity.error = getString(R.string.require_field)
            }
            R.id.etProductDetail -> {
                mBinding.etProductDetail.error = getString(R.string.require_field)
            }
        }
    }

    override fun onValidationSuccess() {
        onProgress()
        when (mViewModel.mode) {
            DataMode.ADD.toString() -> {
                addProduct()
            }
            DataMode.UPDATE.toString() -> {
                updateProduct()
            }
        }
    }

    override fun onProgress() {
        mBinding.progressBar.visibility = View.VISIBLE
        mBinding.btnClose.isEnabled = false
        mBinding.btnAddEditProduct.isEnabled = false
    }

    override fun onResponse() {
        mBinding.progressBar.visibility = View.GONE
        mBinding.btnClose.isEnabled = true
        mBinding.btnAddEditProduct.isEnabled = true
    }

    private fun intiView() {
        if (mViewModel.mode == DataMode.UPDATE.toString()) {
            val name: String = mViewModel.productModel?.productName.toString()
            val purchasePrice: String = mViewModel.productModel?.productPurchasePrice.toString()
            val salePrice: String = mViewModel.productModel?.productSalePrice.toString()
            val quantity: String = mViewModel.productModel?.productQuantity.toString()
            val detail: String = mViewModel.productModel?.productDetail.toString()

            mBinding.etProductName.setText(name)
            mBinding.etProductPurchasePrice.setText(purchasePrice)
            mBinding.etProductSalePrice.setText(salePrice)
            mBinding.etProductQuantity.setText(quantity)
            mBinding.etProductDetail.setText(detail)
        }
    }

    private fun loadDataFromBundle() {
        if (arguments?.isEmpty != true) {
            mViewModel.productModel = arguments?.getSerializable(KEY_DATA) as ProductModel
            mViewModel.mode = DataMode.UPDATE.toString()
        }
    }

    private fun setListeners() {
        mBinding.btnClose.setOnClickListener {
            requireActivity().finish()
        }
        mBinding.btnAddEditProduct.setOnClickListener {
            validateTextField(
                mBinding.etProductName,
                mBinding.etProductPurchasePrice,
                mBinding.etProductSalePrice,
                mBinding.etProductQuantity,
                mBinding.etProductDetail,
            )
        }
    }

    private fun addProduct() {
        val mRef = mFireStoreDbRef.collection(Constants.NODE_PRODUCTS).document()
        val productModel = ProductModel(
            mRef.id,
            mBinding.etProductName.text.toString().trim(),
            mBinding.etProductPurchasePrice.text.toString().toInt(),
            mBinding.etProductSalePrice.text.toString().toInt(),
            mBinding.etProductQuantity.text.toString().toInt(),
            mBinding.etProductDetail.text.toString().trim(),
            FirebaseAuth.getInstance().uid
        )
        mRef.set(productModel).addOnSuccessListener {
            Log.d(TAG, getString(R.string.doc_successfully_written))
            resetFields()
            onResponse()
        }.addOnFailureListener { e ->
            Log.w(TAG, getString(R.string.error_writing_doc), e)
            onResponse()
        }
    }

    private fun updateProduct() {
        val productId = mViewModel.productModel?.productId.toString()
        val userUId = mViewModel.productModel?.userUId.toString()

        val productName = mBinding.etProductName.text.toString()
        val productPurchasePrice = mBinding.etProductPurchasePrice.text.toString().toDouble()
        val productSalePrice = mBinding.etProductSalePrice.text.toString().toDouble()
        val productQuantity = mBinding.etProductQuantity.text.toString().toInt()
        val productDetail = mBinding.etProductDetail.text.toString()

        val mRef = mFireStoreDbRef.collection(Constants.NODE_PRODUCTS).document(productId)

        val docData = hashMapOf(
            NODE_PRODUCT_ID to productId,
            NODE_PRODUCT_NAME to productName,
            NODE_PRODUCT_PURCHASE_PRICE to productPurchasePrice,
            NODE_PRODUCT_SALE_PRICE to productSalePrice,
            NODE_PRODUCT_QUANTITY to productQuantity,
            NODE_PRODUCT_DETAIL to productDetail,
            NODE_USER_ID to userUId
        )

        mRef.set(docData).addOnSuccessListener {
            Log.d(TAG, getString(R.string.doc_successfully_written))
            resetFields()
            onResponse()
        }.addOnFailureListener { e ->
            Log.w(TAG, getString(R.string.error_writing_doc), e)
            onResponse()
        }
    }

    private fun resetFields() {
        mBinding.etProductName.text?.clear()
        mBinding.etProductPurchasePrice.text?.clear()
        mBinding.etProductSalePrice.text?.clear()
        mBinding.etProductQuantity.text?.clear()
        mBinding.etProductDetail.text?.clear()
    }

}