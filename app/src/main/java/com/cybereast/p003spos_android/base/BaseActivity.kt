package com.cybereast.p003spos_android.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.services.NetworkChangeReceiver
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


abstract class BaseActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private lateinit var connectionLiveData: NetworkChangeReceiver
    var mFireStoreDbRef = FirebaseFirestore.getInstance()
    lateinit var mDoctorDocumentRef: DocumentReference
    val mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = NetworkChangeReceiver(this)
        registerInternetBroadCast(window.decorView.findViewById(android.R.id.content))
    }

    override fun onBackPressed() {
        when {
            getEntryCount() <= 1 -> {
                this.finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected open fun setUpActionBar(
        toolbar: Toolbar,
        pTitle: String?,
        isShowTitle: Boolean,
        isShowHome: Boolean
    ) {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(isShowHome)
            supportActionBar?.setDisplayShowTitleEnabled(isShowTitle)
            supportActionBar?.setDisplayShowHomeEnabled(isShowHome)
            //  supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_img)

        }
        toolbar.title = pTitle
    }

    protected open fun setUpActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            // supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_humburger_menu)
        }
    }

    protected fun switchTheme(isDay: Boolean) {
        if (isDay) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    protected open fun replaceFragment(containerViewId: Int, fragment: Fragment) {
        fragmentManager.beginTransaction().replace(containerViewId, fragment)
            .addToBackStack(fragment::class.java.name)
            .commit()
    }

    protected open fun popUpAllFragmentIncludeThis(pClassName: String?) {
        fragmentManager.popBackStack(pClassName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    @SuppressLint("InlinedApi")
    protected open fun changeStatusBarColorTo(colorCode: Int, statusTextColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.navigationBarColor = colorCode
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(statusTextColor)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, colorCode)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.setDecorFitsSystemWindows(false)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.decorView.systemUiVisibility = statusTextColor
                }
            }
        }

    }

    protected open fun getEntryCount(): Int {
        return fragmentManager.backStackEntryCount
    }


    fun registerInternetBroadCast(view: View) {
        val snackBar: Snackbar =
            Snackbar.make(
                view,
                resources.getString(R.string.no_internet),
                Snackbar.LENGTH_INDEFINITE
            )
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(
                this@BaseActivity,
                R.color.colorRed
            )
        )
//        if (view.id == R.id.bottomNavigation) {
//            snackBar.anchorView = view
//        }

        connectionLiveData.observe(this, { isConnected ->
            isConnected?.let {
                if (it) {
                    snackBar.dismiss()
                } else {
                    snackBar.show()
                }
            }
        })

    }


}