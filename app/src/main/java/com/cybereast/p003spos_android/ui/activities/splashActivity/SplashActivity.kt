package com.cybereast.p003spos_android.ui.activities.splashActivity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseActivity
import com.cybereast.p003spos_android.constants.Constants.SPLASH_TIME_OUT
import com.cybereast.p003spos_android.ui.activities.dashBoardActivity.DashboardActivity
import com.cybereast.p003spos_android.ui.activities.loginActivity.LoginActivity
import com.cybereast.p003spos_android.utils.ActivityUtils

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        openChooserActivity()
    }

    private fun openChooserActivity() {
        val mRunnable = Runnable {
            if (!isFinishing) {
                checkUserSession()
                finish()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(mRunnable, SPLASH_TIME_OUT)
    }

    private fun checkUserSession() {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val bundle = Bundle()
            ActivityUtils.startActivity(this, DashboardActivity::class.java)
        } else {
            ActivityUtils.startActivity(this, LoginActivity::class.java)
        }
    }
}