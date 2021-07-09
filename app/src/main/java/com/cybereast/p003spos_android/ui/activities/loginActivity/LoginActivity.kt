package com.cybereast.p003spos_android.ui.activities.loginActivity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseValidationActivity
import com.cybereast.p003spos_android.databinding.ActivityLoginBinding
import com.cybereast.p003spos_android.ui.activities.dashBoardActivity.DashboardActivity
import com.cybereast.p003spos_android.ui.activities.signUpActivity.SignUpActivity
import com.cybereast.p003spos_android.utils.ActivityUtils
import com.cybereast.p003spos_android.utils.AppUtils
import com.cybereast.p003spos_android.utils.InputValidation
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseValidationActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mFirebaseAuthListener: FirebaseAuth.AuthStateListener
    override fun onValidationError(editText: EditText) {
        editText.error = getString(R.string.required_field)
    }

    override fun onValidationSuccess() {
        if (InputValidation.isValidEmail(this, mBinding.userEmailEt)) {
            login()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setListeners()
    }


    private fun login() {
        AppUtils.showHideProgressBar(mBinding.progressBar, View.VISIBLE)
        mAuth.signInWithEmailAndPassword(
            mBinding.userEmailEt.text.toString(),
            mBinding.userPasswordEt.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)
                ActivityUtils.startActivity(this, DashboardActivity::class.java)
                finish()
            } else {
                AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)
                AppUtils.showToast(this, getString(R.string.invalid_email_password))
            }
        }
    }

    private fun setListeners() {
        mBinding.loginBtn.setOnClickListener {
            validateTextField(mBinding.userEmailEt, mBinding.userPasswordEt)
        }
        mBinding.signUpTv.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
            finish()
        }
    }


}