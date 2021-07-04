package com.cybereast.p003spos_android.base

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.p003spos_android.data.adapter.VerticalSpaceItemDecoration

abstract class RecyclerViewBaseDialogFragment : BaseDialogFragment() {
    protected open fun setUpRecyclerView(pRecyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        pRecyclerView.layoutManager = linearLayoutManager
        pRecyclerView.itemAnimator = DefaultItemAnimator()
        val mAdapter: RecyclerView.Adapter<*> = onPrepareAdapter()
        pRecyclerView.adapter = mAdapter
    }

    protected open fun setUpRecyclerView(pRecyclerView: RecyclerView, verticalSpacing: Int) {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        pRecyclerView.layoutManager = linearLayoutManager
        val mItemDecorator = VerticalSpaceItemDecoration()
        mItemDecorator.VerticalSpaceItemDecoration(verticalSpacing)
        pRecyclerView.addItemDecoration(mItemDecorator)
        pRecyclerView.itemAnimator = DefaultItemAnimator()
        val mAdapter: RecyclerView.Adapter<*> = onPrepareAdapter()
        pRecyclerView.adapter = mAdapter
    }

    protected abstract fun onPrepareAdapter(): RecyclerView.Adapter<*>
}