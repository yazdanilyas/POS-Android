package com.cybereast.p003spos_android.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.cybereast.p003spos_android.R

object DialogUtils {
    fun infoAlert(pContext: Context, pMessage: String, callBack: AlertCallBack) {
        val dialog = Dialog(pContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_alert_dialog)
        val message = dialog.findViewById(R.id.tvMessage) as AppCompatTextView
        val btnOk = dialog.findViewById(R.id.btnOk) as AppCompatTextView
        message.text = pMessage
        btnOk.text = pContext.resources.getString(R.string.ok)
        btnOk.setOnClickListener {
            callBack.onOkCallBack()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun suretyDialog(pContext: Context, mMessage: String, pTitle: String, pCallBack: CallBack) {
        val suretyDialog = AlertDialog.Builder(pContext).create()
        suretyDialog.setTitle(pTitle)
        suretyDialog.setCancelable(false)
        suretyDialog.setMessage(mMessage)
        suretyDialog.setButton(
            AlertDialog.BUTTON_POSITIVE,
            pContext.resources.getString(R.string.yes)
        ) { dialog, _ ->
            pCallBack.onPositiveCallBack()
            dialog.dismiss()
        }
        suretyDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            pContext.resources.getString(R.string.no)
        ) { dialog, _ ->
            pCallBack.onNegativeCallBack()
            dialog.dismiss()
        }
        suretyDialog.show()
    }

    fun suretyDialog(
        pContext: Context,
        pTitle: String,
        pMessage: String,
        isShowTitle: Boolean,
        pCallBack: CallBack
    ) {

        val dialog = Dialog(pContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_surety)

        val title = dialog.findViewById(R.id.tvTitle) as TextView
        val message = dialog.findViewById(R.id.tvSurelyMessage) as TextView
        val yesBtn = dialog.findViewById(R.id.btnYes) as TextView
        val noBtn = dialog.findViewById(R.id.btnNo) as TextView

        message.text = pMessage

        if (isShowTitle)
            title.visibility = View.VISIBLE
        else
            title.visibility = View.GONE

        yesBtn.setOnClickListener {
            pCallBack.onPositiveCallBack()
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            pCallBack.onNegativeCallBack()
            dialog.dismiss()
        }
        dialog.show()
    }

    interface CallBack {
        fun onPositiveCallBack()
        fun onNegativeCallBack()
    }

    interface AlertCallBack {
        fun onOkCallBack()
    }


}