package com.cybereast.p003spos_android.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cybereast.p003spos_android.databinding.ActivityMainBinding
import com.cybereast.p003spos_android.utils.AppUtils

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setListeners()

    }

    private fun setListeners() {
        mBinding.hello.setOnClickListener {
            AppUtils.logOut(this)
        }
    }

}