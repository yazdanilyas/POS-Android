package com.cybereast.p003spos_android.ui.fragments.moreFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.databinding.MoreFragmentBinding
import com.cybereast.p003spos_android.ui.activities.loginActivity.LoginActivity
import com.cybereast.p003spos_android.utils.ActivityUtils
import com.cybereast.p003spos_android.utils.AppUtils
import com.google.firebase.auth.FirebaseAuth

class MoreFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = MoreFragment()
    }

    private lateinit var moreViewModel: MoreViewModel
    private lateinit var mBinding: MoreFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MoreFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moreViewModel =
            ViewModelProvider(this).get(MoreViewModel::class.java)
        setListeners()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.shareAppCv -> {
                AppUtils.shareApp(requireActivity())
            }
            R.id.rateAppCv -> {
                AppUtils.rateApp(requireActivity())
            }
            R.id.logoutCv -> {
                logOut()
            }
        }
    }

    private fun setListeners() {
        mBinding.shareAppCv.setOnClickListener(this)
        mBinding.rateAppCv.setOnClickListener(this)
        mBinding.logoutCv.setOnClickListener(this)
    }


    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        ActivityUtils.startActivity(requireActivity(), LoginActivity::class.java)
        activity?.finish()
    }

}