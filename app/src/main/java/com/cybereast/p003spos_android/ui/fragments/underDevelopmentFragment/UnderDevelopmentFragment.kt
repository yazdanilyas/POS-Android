package com.cybereast.p003spos_android.ui.fragments.underDevelopmentFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.R

class UnderDevelopmentFragment : Fragment() {

    companion object {
        fun newInstance() = UnderDevelopmentFragment()
    }

    private lateinit var viewModel: UnderDevelopmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.under_development_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UnderDevelopmentViewModel::class.java)
    }

}