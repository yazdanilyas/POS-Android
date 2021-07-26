package com.cybereast.p003spos_android.ui.fragments.invoiceManagementFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.databinding.InvoiceManagementFragmentBinding
import com.cybereast.p003spos_android.ui.fragments.invoiceListFragment.InvoiceListFragment
import com.cybereast.p003spos_android.utils.ActivityUtils

class InvoiceManagementFragment : Fragment() {

    companion object {
        fun newInstance() = InvoiceManagementFragment()
    }

    private lateinit var viewModel: InvoiceManagementViewModel
    private lateinit var mBinding: InvoiceManagementFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = InvoiceManagementFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(InvoiceManagementViewModel::class.java)
        setListeners()
    }

    private fun setListeners() {
        mBinding.invoiceListCv.setOnClickListener {
            ActivityUtils.launchFragment(
                requireContext(),
                InvoiceListFragment::class.java.name
            )
        }
    }

}