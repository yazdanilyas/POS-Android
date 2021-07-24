package com.cybereast.p003spos_android.ui.fragments.ledgerFragment

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
import com.cybereast.p003spos_android.data.adapter.RecyclerViewAdapter
import com.cybereast.p003spos_android.databinding.LedgerFragmentBinding
import com.cybereast.p003spos_android.models.LedgerModel
import com.google.firebase.firestore.QueryDocumentSnapshot

class LedgerFragment : RecyclerViewBaseFragment(),
    RecyclerViewAdapter.CallBack, BaseInterface {

    companion object {
        fun newInstance() = LedgerFragment()
    }

    private lateinit var mViewModel: LedgerViewModel
    private lateinit var mBinding: LedgerFragmentBinding
    private lateinit var mAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = LedgerFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(LedgerViewModel::class.java)
        setAdapter()
        readLedgerDataFromFirebase()

    }

    override fun onPrepareAdapter(): RecyclerView.Adapter<*> {
        return if (::mAdapter.isInitialized) {
            mAdapter
        } else {
            mAdapter = RecyclerViewAdapter(this, mViewModel.mLedgerList)
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
        return R.layout.item_ledger
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
        mAdapter = RecyclerViewAdapter(this, mViewModel.mLedgerList)
        setUpRecyclerView(
            mBinding.ledgerRecyclerView,
            resources.getDimensionPixelSize(R.dimen._5dp) / 2
        )
        mBinding.ledgerRecyclerView.setHasFixedSize(true)
    }

    private fun readLedgerDataFromFirebase() {
        mFireStoreDbRef.collection(Constants.NODE_LEDGER).orderBy(Constants.NODE_DATE)
            .addSnapshotListener { snap, e ->
                try {
                    if (e != null) {
                        Log.w(TAG, "Listen failed ledger data.", e)
                        return@addSnapshotListener
                    }
                    mViewModel.mLedgerList.clear()
                    for (doc: QueryDocumentSnapshot in snap!!) {
                        doc.toObject(LedgerModel::class.java).let {
                            mViewModel.mLedgerList.add(it)
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load ledger data", e)
                } finally {
                    mAdapter.notifyDataSetChanged()
                    mBinding.ledgerRecyclerView.scrollToPosition(mViewModel.mLedgerList.size - 1)
                    calculateBill()
                }
            }
    }

    private fun calculateBill() {
        var totalCr = 0
        var totalDr = 0
        var balance = 0
        for (i in 0 until mViewModel.mLedgerList.size) {
            val model = mViewModel.mLedgerList[i] as LedgerModel
            totalCr += model.cr ?: 0
            totalDr += model.dr ?: 0
        }
        balance = totalDr.minus(totalCr)

        mBinding.layoutFooter.tvCr.text = "Rs$totalCr"
        mBinding.layoutFooter.tvDr.text = "Rs$totalDr"
        mBinding.layoutFooter.tvBalanceValue.text = "Rs$balance/-"
    }

}