package com.cybereast.p003spos_android.ui.fragments.invoiceFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseInterface
import com.cybereast.p003spos_android.base.RecyclerViewBaseFragment
import com.cybereast.p003spos_android.constants.Constants
import com.cybereast.p003spos_android.data.adapter.RecyclerViewAdapter
import com.cybereast.p003spos_android.data.interfaces.ChooseProductCallBack
import com.cybereast.p003spos_android.databinding.InvoiceFragmentBinding
import com.cybereast.p003spos_android.models.ProductModel
import com.cybereast.p003spos_android.ui.fragments.fragmentDialogs.choseProductDialogFragment.ChoseProductDialogFragment
import java.util.*

class InvoiceFragment : RecyclerViewBaseFragment(),
    RecyclerViewAdapter.CallBack, BaseInterface, ChooseProductCallBack {

    companion object {
        fun newInstance() = InvoiceFragment()
    }

    private lateinit var choseProductDialog: ChoseProductDialogFragment

    private lateinit var mViewModel: InvoiceViewModel
    private lateinit var mBinding: InvoiceFragmentBinding
    private lateinit var mAdapter: RecyclerViewAdapter
    private var timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = InvoiceFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(InvoiceViewModel::class.java)
        setListeners()
        setAdapter()
    }

    private fun setListeners() {
        mBinding.addProductButton.setOnClickListener {
            openChoseProductDialog()
        }
    }

    private fun openChoseProductDialog() {
        choseProductDialog = ChoseProductDialogFragment.newInstance(this)
        choseProductDialog.show(
            requireActivity().supportFragmentManager,
            choseProductDialog.tag
        )
        mBinding.addProductButton.isEnabled = false
    }

    private fun closeChoseProductDialog() {
        choseProductDialog.dismiss()
        mBinding.addProductButton.isEnabled = true
    }

    override fun onPrepareAdapter(): RecyclerView.Adapter<*> {
        return if (::mAdapter.isInitialized) {
            mAdapter
        } else {
            mAdapter = RecyclerViewAdapter(this, mViewModel.mProductList)
            mAdapter
        }
    }

    override fun onViewClicked(view: View, data: Any?) {
        val productModel = data as ProductModel
        when (view.id) {
            R.id.tvDecreaseQuantity -> {
                val selected = productModel.selectedQuantity ?: 0
                val total = productModel.productQuantity ?: 0
                if (selected < total)
                    productModel.selectedQuantity = selected.plus(1)

            }
            R.id.tvIncreaseQuantityImg -> {

            }
        }
    }

    override fun onItemClick(data: Any?, position: Int) {
    }

    override fun onItemLongClick(view: View, data: Any?, position: Int) {
    }

    override fun inflateLayoutFromId(position: Int, data: Any?): Int {
        return R.layout.item_invoice
    }

    override fun onNoDataFound() {
    }

    override fun onProgress() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    override fun onResponse() {
        mBinding.progressBar.visibility = View.GONE
    }

    override fun onProductSelected(product: ProductModel) {
        closeChoseProductDialog()
        mViewModel.mProductList.add(product)
        mAdapter.notifyDataSetChanged()
    }

    private fun setAdapter() {
        mAdapter = RecyclerViewAdapter(this, mViewModel.mProductList)
        setUpRecyclerView(
            mBinding.recyclerView,
            resources.getDimensionPixelSize(R.dimen._5dp) / 2
        )
        mBinding.recyclerView.setHasFixedSize(true)
    }

    private fun updateQuantityDelay() {
        timer.cancel()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                requireActivity().runOnUiThread {
                    updateProduct()
                }
            }
        }, Constants.REQUEST_DELAY)
    }

    private fun updateProduct() {

    }
}