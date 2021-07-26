package com.cybereast.p003spos_android.ui.fragments.invoiceProductListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseInterface
import com.cybereast.p003spos_android.base.RecyclerViewBaseFragment
import com.cybereast.p003spos_android.data.adapter.RecyclerViewAdapter
import com.cybereast.p003spos_android.databinding.InvoiceProductListFragmentBinding
import com.cybereast.p003spos_android.models.InvoiceModel
import com.cybereast.p003spos_android.models.ProductModel
import com.cybereast.p003spos_android.utils.CommonKeys.KEY_DATA


class InvoiceProductListFragment : RecyclerViewBaseFragment(),
    RecyclerViewAdapter.CallBack, BaseInterface {

    companion object {
        fun newInstance() = InvoiceProductListFragment()
    }

    private lateinit var mViewModel: InvoiceProductListViewModel
    private lateinit var mBinding: InvoiceProductListFragmentBinding
    private lateinit var mAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = InvoiceProductListFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(InvoiceProductListViewModel::class.java)
        setAdapter()
        loadDataFromBundle()
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
    }

    override fun onItemClick(data: Any?, position: Int) {
    }

    override fun onItemLongClick(view: View, data: Any?, position: Int) {
    }

    override fun inflateLayoutFromId(position: Int, data: Any?): Int {
        return R.layout.item_invoice_product
    }

    override fun onNoDataFound() {
    }

    override fun onProgress() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    override fun onResponse() {
        mBinding.progressBar.visibility = View.GONE
    }

    private fun loadDataFromBundle() {
        if (arguments?.isEmpty != true) {
            mViewModel.mInvoiceModel = arguments?.getSerializable(KEY_DATA) as InvoiceModel
            val list = mViewModel.mInvoiceModel?.productList as ArrayList<ProductModel>
            mViewModel.mProductList.addAll(list)
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        mAdapter = RecyclerViewAdapter(this, mViewModel.mProductList)
        setUpRecyclerView(
            mBinding.recyclerView,
            resources.getDimensionPixelSize(R.dimen._5dp) / 2
        )
        mBinding.recyclerView.setHasFixedSize(true)
    }
}
