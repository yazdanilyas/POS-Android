package com.cybereast.p003spos_android.ui.fragments.purchasesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.R

class PurchaseManagementFragment : Fragment() {

    private lateinit var purchaseManagementViewModel: PurchaseManagementViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        purchaseManagementViewModel =
            ViewModelProvider(this).get(PurchaseManagementViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_purchases_management, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        purchaseManagementViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}