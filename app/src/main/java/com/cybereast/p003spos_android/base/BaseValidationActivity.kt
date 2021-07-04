package com.cybereast.p003spos_android.base

import android.text.TextUtils
import android.widget.EditText

abstract class BaseValidationActivity : BaseActivity() {
    companion object {
        val TAG: String = BaseValidationActivity::class.java.name
    }

    protected fun validateTextField(vararg textField: EditText) {
        var validation = true
        for (textF in textField) {
            if (TextUtils.isEmpty(textF.text)) {
                validation = false
                onValidationError(textF)
            }
        }
        if (validation) {
            onValidationSuccess()
        }

    }

    abstract fun onValidationError(editText: EditText)
    abstract fun onValidationSuccess()
}