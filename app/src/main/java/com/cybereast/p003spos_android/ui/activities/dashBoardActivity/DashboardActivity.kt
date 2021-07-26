package com.cybereast.p003spos_android.ui.activities.dashBoardActivity

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseActivity
import com.cybereast.p003spos_android.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity() {
    private lateinit var mBinding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_stock,
                R.id.navigation_purchases,
                R.id.navigation_ledger,
                R.id.navigation_invoice,
                R.id.navigation_more
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        mBinding.bottomBar.setupWithNavController(navController)
        registerInternetBroadCast(mBinding.bottomBar)
    }
}