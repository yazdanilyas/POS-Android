package com.cybereast.p003spos_android.ui.fragments.addEditProductFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseValidationFragment
import com.cybereast.p003spos_android.databinding.AddEditProductFragmentBinding
import com.cybereast.p003spos_android.model.ProductModel

class AddEditProductFragment : BaseValidationFragment() {

    companion object {
        fun newInstance() = AddEditProductFragment()
    }

    private lateinit var viewModel: AddEditProductViewModel
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
        viewModel = ViewModelProvider(this).get(AddEditProductViewModel::class.java)
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
        val productModel = ProductModel(
            0,
            mBinding.etProductName.text.toString().trim(),
            mBinding.etProductPurchasePrice.text.toString().toDouble(),
            mBinding.etProductSalePrice.text.toString().toDouble(),
            mBinding.etProductQuantity.text.toString().toInt(),
            mBinding.etProductDetail.text.toString().trim()
        )
        addProductToFireBase(productModel)
    }

    private fun addProductToFireBase(productModel: ProductModel) {

    }


}