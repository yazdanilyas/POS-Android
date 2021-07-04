package com.cybereast.p003spos_android.ui.fragments.productListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.RecyclerViewBaseFragment
import com.cybereast.p003spos_android.data.adapter.RecyclerViewAdapter
import com.cybereast.p003spos_android.data.interfaces.BaseInterface
import com.cybereast.p003spos_android.databinding.ProductListFragmentBinding
import com.cybereast.p003spos_android.model.ProductModel

class ProductListFragment : RecyclerViewBaseFragment(),
    RecyclerViewAdapter.CallBack, BaseInterface {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    private lateinit var mViewModel: ProductListViewModel
    private lateinit var mBinding: ProductListFragmentBinding
    private lateinit var mAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ProductListFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
    }

    override fun onPrepareAdapter(): RecyclerView.Adapter<*> {
        return if (::mAdapter.isInitialized) {
            mAdapter
        } else {
            mAdapter = RecyclerViewAdapter(this, mViewModel.mProductList)
            mAdapter
        }
    }

    override fun showProgressBar() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        mBinding.progressBar.visibility = View.GONE
    }

    override fun onViewClicked(view: View, data: Any?) {
        mViewModel.mProductModel = data as ProductModel
        when (view.id) {
//            R.id.btnDeleteProduct -> {
//                mChatViewModel.mMessage.id.let {
//                    updateMessage(
//                        it,
//                        RequestStatus.ACCEPT.toString()
//                    )
//                }
//            }
//            R.id.btnUpdateProduct -> {
//                mChatViewModel.mMessage.id?.let {
//                    updateMessage(
//                        it,
//                        RequestStatus.REJECT.toString()
//                    )
//                }
//            }
        }
    }

    override fun onItemClick(data: Any?, position: Int) {
//        val message = data as ProductModel
//        if (message.requestStatus.toString() == RequestStatus.ACCEPT.toString()) {
//            val bundle = Bundle().apply {
//                putSerializable(CommonKeys.KEY_DATA, mViewModel.mProductModel)
//            }
//            ActivityUtils.launchFragment(
//                requireContext(),
//                AddEditProductFragment::class.java.name, bundle
//            )
//        }

    }

    override fun onItemLongClick(view: View, data: Any?, position: Int) {
    }

    override fun inflateLayoutFromId(position: Int, data: Any?): Int {
        return R.layout.item_product
    }

    override fun onNoDataFound() {
    }

}