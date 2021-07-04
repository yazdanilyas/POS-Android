package com.cybereast.p003spos_android.ui.fragments.addEditProductFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseValidationFragment
import com.cybereast.p003spos_android.constants.Constants
import com.cybereast.p003spos_android.databinding.AddEditProductFragmentBinding
import com.cybereast.p003spos_android.model.ProductModel

class AddEditProductFragment : BaseValidationFragment() {

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
        setListeners()
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
        val mRef = mFireStoreDbRef.collection(Constants.NODE_PRODUCTS).document()

        val productModel = ProductModel(
            mRef.id,
            mBinding.etProductName.text.toString().trim(),
            mBinding.etProductPurchasePrice.text.toString().toDouble(),
            mBinding.etProductSalePrice.text.toString().toDouble(),
            mBinding.etProductQuantity.text.toString().toInt(),
            mBinding.etProductDetail.text.toString().trim()
        )
        mRef.set(productModel).addOnSuccessListener {
            Log.d(TAG, getString(R.string.doc_successfully_written))
            resetFields()
        }.addOnFailureListener { e ->
            Log.w(TAG, getString(R.string.error_writing_doc), e)
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