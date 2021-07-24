package com.cybereast.p003spos_android.ui.fragments.productListFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseInterface
import com.cybereast.p003spos_android.base.BaseValidationFragment
import com.cybereast.p003spos_android.base.RecyclerViewBaseFragment
import com.cybereast.p003spos_android.constants.Constants
import com.cybereast.p003spos_android.data.adapter.RecyclerViewAdapter
import com.cybereast.p003spos_android.databinding.ProductListFragmentBinding
import com.cybereast.p003spos_android.models.ProductModel
import com.cybereast.p003spos_android.ui.fragments.addEditProductFragment.AddEditProductFragment
import com.cybereast.p003spos_android.utils.ActivityUtils
import com.cybereast.p003spos_android.utils.CommonKeys
import com.google.firebase.firestore.QueryDocumentSnapshot

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
                    deleteProduct(productModel)
                }
            }
            R.id.btnUpdateProduct -> {
                val bundle = Bundle().apply {
                    putSerializable(CommonKeys.KEY_DATA, mViewModel.mProductModel)
                }
                ActivityUtils.launchFragment(
                    requireContext(),
                    AddEditProductFragment::class.java.name, bundle
                )
            }
        }
    }

    override fun onItemClick(data: Any?, position: Int) {
    }

    override fun onItemLongClick(view: View, data: Any?, position: Int) {
    }

    override fun inflateLayoutFromId(position: Int, data: Any?): Int {
        return R.layout.item_product
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
                    Log.w(TAG, "Listen failed.", e)
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
                Log.e(TAG, "Failed to load  products", e)
            } finally {
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun deleteProduct(model: ProductModel) {
        model.productId?.let {
            val mRef = mFireStoreDbRef.collection(Constants.NODE_PRODUCTS).document(it)
            mRef.delete().addOnSuccessListener {
                Log.d(BaseValidationFragment.TAG, getString(R.string.doc_successfully_written))
                onResponse()
            }.addOnFailureListener { e ->
                Log.w(BaseValidationFragment.TAG, getString(R.string.error_writing_doc), e)
                onResponse()
            }
        }

    }
}