package com.cybereast.p003spos_android.ui.fragments.purchasesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.databinding.FragmentPurchasesManagementBinding
import com.cybereast.p003spos_android.ui.fragments.invoiceFragment.InvoiceFragment
import com.cybereast.p003spos_android.utils.ActivityUtils

class PurchaseManagementFragment : Fragment() {

    private lateinit var purchaseManagementViewModel: PurchaseManagementViewModel
    private lateinit var mBinding: FragmentPurchasesManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        purchaseManagementViewModel =
            ViewModelProvider(this).get(PurchaseManagementViewModel::class.java)
        mBinding = FragmentPurchasesManagementBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        mBinding.addInvoiceCv.setOnClickListener {
            ActivityUtils.launchFragment(
                requireContext(),
                InvoiceFragment::class.java.name
            )
        }

    }
}