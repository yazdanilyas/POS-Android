package com.cybereast.p003spos_android.ui.fragments.stockmanagementFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.databinding.FragmentStockManagmentBinding
import com.cybereast.p003spos_android.ui.fragments.addEditProductFragment.AddEditProductFragment
import com.cybereast.p003spos_android.ui.fragments.productListFragment.ProductListFragment
import com.cybereast.p003spos_android.ui.fragments.underDevelopmentFragment.UnderDevelopmentFragment
import com.cybereast.p003spos_android.utils.ActivityUtils

class StockManagementFragment : Fragment() {

    private lateinit var stockManagementViewModel: StockManagementViewModel
    private lateinit var mBinding: FragmentStockManagmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        stockManagementViewModel =
            ViewModelProvider(this).get(StockManagementViewModel::class.java)
        mBinding = FragmentStockManagmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        mBinding.addProductCv.setOnClickListener {
            ActivityUtils.launchFragment(
                requireContext(),
                AddEditProductFragment::class.java.name
            )
        }
        mBinding.updateStockCv.setOnClickListener {
            ActivityUtils.launchFragment(
                requireContext(),
                UnderDevelopmentFragment::class.java.name
            )
        }

        mBinding.productListCv.setOnClickListener {
            ActivityUtils.launchFragment(
                requireContext(),
                ProductListFragment::class.java.name
            )
        }
    }
}