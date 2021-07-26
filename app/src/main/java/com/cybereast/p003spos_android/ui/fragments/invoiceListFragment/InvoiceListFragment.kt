package com.cybereast.p003spos_android.ui.fragments.invoiceListFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseInterface
import com.cybereast.p003spos_android.base.RecyclerViewBaseFragment
import com.cybereast.p003spos_android.constants.Constants
import com.cybereast.p003spos_android.constants.Constants.NODE_INVOICE
import com.cybereast.p003spos_android.data.adapter.RecyclerViewAdapter
import com.cybereast.p003spos_android.databinding.InvoiceListFragmentBinding
import com.cybereast.p003spos_android.models.InvoiceModel
import com.cybereast.p003spos_android.ui.fragments.invoiceProductListFragment.InvoiceProductListFragment
import com.cybereast.p003spos_android.utils.ActivityUtils
import com.cybereast.p003spos_android.utils.CommonKeys.KEY_DATA
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot

class InvoiceListFragment : RecyclerViewBaseFragment(),
    RecyclerViewAdapter.CallBack, BaseInterface {

    companion object {
        fun newInstance() = InvoiceListFragment()
    }

    private lateinit var mViewModel: InvoiceListViewModel
    private lateinit var mBinding: InvoiceListFragmentBinding
    private lateinit var mAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = InvoiceListFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar(mBinding.appToolbar.toolbar, getString(R.string.invoice_history), true)
        mViewModel = ViewModelProvider(this).get(InvoiceListViewModel::class.java)
        setAdapter()
        getInvoiceProductsListFirebase()
    }

    override fun onPrepareAdapter(): RecyclerView.Adapter<*> {
        return if (::mAdapter.isInitialized) {
            mAdapter
        } else {
            mAdapter = RecyclerViewAdapter(this, mViewModel.mInvoiceList)
            mAdapter
        }
    }

    override fun onViewClicked(view: View, data: Any?) {

    }

    override fun onItemClick(data: Any?, position: Int) {
        val model = data as InvoiceModel
        val bundle = Bundle()
        bundle.putSerializable(KEY_DATA, model)
        ActivityUtils.launchFragment(
            requireContext(),
            InvoiceProductListFragment::class.java.name,
            bundle
        )
    }

    override fun onItemLongClick(view: View, data: Any?, position: Int) {
    }

    override fun inflateLayoutFromId(position: Int, data: Any?): Int {
        return R.layout.item_invoice_detail
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
        mAdapter = RecyclerViewAdapter(this, mViewModel.mInvoiceList)
        setUpRecyclerView(
            mBinding.recyclerView,
            resources.getDimensionPixelSize(R.dimen._5dp) / 2
        )
        mBinding.recyclerView.setHasFixedSize(true)
    }

    private fun getInvoiceProductsListFirebase() {
        mFireStoreDbRef.collection(NODE_INVOICE)
            .whereEqualTo(Constants.FIELD_USER_ID, FirebaseAuth.getInstance().uid)
            .addSnapshotListener { snap, e ->
                try {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    mViewModel.mInvoiceList.clear()
                    if (snap != null) {
                        for (doc: QueryDocumentSnapshot in snap) {
                            doc.toObject(InvoiceModel::class.java).let { invoiceModel ->
                                mViewModel.mInvoiceList.add(invoiceModel)
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
}