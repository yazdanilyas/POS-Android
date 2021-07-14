package com.cybereast.p003spos_android.ui.fragments.fragmentDialogs.choseProductDialogFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseFragment
import com.cybereast.p003spos_android.base.BaseInterface
import com.cybereast.p003spos_android.base.RecyclerViewBaseDialogFragment
import com.cybereast.p003spos_android.constants.Constants
import com.cybereast.p003spos_android.data.adapter.RecyclerViewAdapter
import com.cybereast.p003spos_android.data.interfaces.ChooseProductCallBack
import com.cybereast.p003spos_android.databinding.ChoseProductDialogFragmentBinding
import com.cybereast.p003spos_android.models.ProductModel
import com.google.firebase.firestore.QueryDocumentSnapshot

class ChoseProductDialogFragment : RecyclerViewBaseDialogFragment(), RecyclerViewAdapter.CallBack,
    BaseInterface {

    companion object {
        fun newInstance(callBack: ChooseProductCallBack): ChoseProductDialogFragment {
            val mFragment = ChoseProductDialogFragment()
            mFragment.setCallback(callBack)
            return mFragment
        }
    }

    private lateinit var mViewModel: ChoseProductDialogViewModel
    private lateinit var mBinding: ChoseProductDialogFragmentBinding
    private lateinit var mAdapter: RecyclerViewAdapter
    private lateinit var mChooseProductCallBack: ChooseProductCallBack


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = ChoseProductDialogFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ChoseProductDialogViewModel::class.java)
        setAdapter()
        getProductsListFirebase()
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
        mViewModel.mProductModel = data as ProductModel
        when (view.id) {
            R.id.btnDeleteProduct -> {
                mViewModel.mProductModel?.let { productModel ->

                }
            }
        }
    }

    override fun onItemClick(data: Any?, position: Int) {
        mChooseProductCallBack.onProductSelected(data as ProductModel)
    }

    override fun onItemLongClick(view: View, data: Any?, position: Int) {
    }

    override fun inflateLayoutFromId(position: Int, data: Any?): Int {
        return R.layout.item_chose_product
    }

    override fun onNoDataFound() {
    }


    override fun onProgress() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    override fun onResponse() {
        mBinding.progressBar.visibility = View.GONE
    }

    private fun setAdapter() {
        mAdapter = RecyclerViewAdapter(this, mViewModel.mProductList)
        setUpRecyclerView(
            mBinding.recyclerView,
            resources.getDimensionPixelSize(R.dimen._5dp) / 2
        )
        mBinding.recyclerView.setHasFixedSize(true)
    }

    private fun getProductsListFirebase() {
        mFireStoreDbRef.collection(Constants.NODE_PRODUCTS).addSnapshotListener { snap, e ->
            try {
                if (e != null) {
                    Log.w(BaseFragment.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                mViewModel.mProductList.clear()
                if (snap != null) {
                    for (doc: QueryDocumentSnapshot in snap) {
                        doc.toObject(ProductModel::class.java).let {
                            mViewModel.mProductList.add(it)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(BaseFragment.TAG, "Failed to load  products", e)
            } finally {
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setCallback(callBack: ChooseProductCallBack) {
        mChooseProductCallBack = callBack
    }
}