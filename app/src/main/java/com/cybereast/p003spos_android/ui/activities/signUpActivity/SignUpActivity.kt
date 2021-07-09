package com.cybereast.p003spos_android.ui.activities.signUpActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseValidationActivity
import com.cybereast.p003spos_android.constants.Constants.COLLECTION_USERS
import com.cybereast.p003spos_android.data.interfaces.BaseInterface
import com.cybereast.p003spos_android.databinding.ActivitySignUpBinding
import com.cybereast.p003spos_android.models.User
import com.cybereast.p003spos_android.ui.activities.dashBoardActivity.DashboardActivity
import com.cybereast.p003spos_android.ui.activities.loginActivity.LoginActivity
import com.cybereast.p003spos_android.utils.ActivityUtils
import com.cybereast.p003spos_android.utils.AppUtils.showToast
import com.cybereast.p003spos_android.utils.InputValidation.isValidEmail
import com.cybereast.p003spos_android.utils.InputValidation.isValidPassword
import com.cybereast.p003spos_android.utils.InputValidation.isValidPhone
import java.util.*

class SignUpActivity : BaseValidationActivity(), BaseInterface {
    private lateinit var mBinding: ActivitySignUpBinding
    private val dummyMap = mapOf<String, Any>("dummyKey" to "dummyValue")
    override fun onValidationError(editText: EditText) {
        editText.error = getString(R.string.required_field)
    }

    override fun onValidationSuccess() {
        if (isValidEmail(applicationContext, mBinding.emailEt)
            && isValidPhone(
                applicationContext,
                mBinding.mobileNoEt
            )
            && isValidPassword(applicationContext, mBinding.passwordEt)
        ) {

            signUp()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setListeners()
    }

    private fun signUp() {
        showProgressBar()
        val user = User(
            null,
            mBinding.firstNameEt.text.toString().capitalize(Locale.ENGLISH),
            mBinding.lastNameEt.text.toString().capitalize(Locale.ENGLISH),
            mBinding.emailEt.text.toString(),
            mBinding.mobileNoEt.text.toString(),
            mBinding.passwordEt.text.toString()
        )
        mAuth.createUserWithEmailAndPassword(user.email.toString(), user.password.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val uId = it.result?.user?.uid
                    if (uId != null) {
                        user.userId = uId
                        mDoctorDocumentRef =
                            mFireStoreDbRef.collection(COLLECTION_USERS).document(uId)
                        mDoctorDocumentRef.set(user).addOnSuccessListener {
                            hideProgressBar()
                            ActivityUtils.startActivity(this, DashboardActivity::class.java)
                            this.finish()
                            showToast(
                                applicationContext,
                                getString(R.string.sign_up_success_message)
                            )
                        }.addOnFailureListener {
                            showProgressBar()
                            showToast(applicationContext, it.message.toString())
                            Log.d("TAG", "signUp: ${it.message}")
                        }
                    }
                } else {
                    hideProgressBar()
                    showToast(applicationContext, it.exception?.message.toString())
                    Log.d("TAG", it.exception?.message.toString())
                }
            }

    }


    private fun setListeners() {
        mBinding.signUpButton.setOnClickListener {
            validateTextField(
                mBinding.firstNameEt,
                mBinding.lastNameEt,
                mBinding.emailEt,
                mBinding.mobileNoEt,
                mBinding.passwordEt,
            )
        }
        mBinding.loginTv.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

    override fun showProgressBar() {
        mBinding.progressBar.visibility = View.VISIBLE
        mBinding.signUpButton.isEnabled = false
    }

    override fun hideProgressBar() {
        mBinding.progressBar.visibility = View.GONE
        mBinding.signUpButton.isEnabled = true
    }


}