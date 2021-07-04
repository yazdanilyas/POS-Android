package com.cybereast.p003spos_android.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.application.POSApplication
import com.cybereast.p003spos_android.ui.activities.loginActivity.LoginActivity
import com.google.firebase.auth.FirebaseAuth

object AppUtils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showHideProgressBar(progressBar: ProgressBar, hideShow: Int) {
        progressBar.visibility = hideShow
    }

    fun clearInputs(vararg editText: AppCompatEditText) {
        for (et in editText) {
            et.text?.clear()
        }
    }


    fun loadImageWithGide(imageContainer: ImageView, url: String) {
        val circularProgressDrawable = imageContainer.context?.let { CircularProgressDrawable(it) }
        circularProgressDrawable?.strokeWidth = 5f
        circularProgressDrawable?.centerRadius = 30f
        circularProgressDrawable?.start()
        Glide.with(POSApplication.getAppContext())
            .load(url)
            .error(R.drawable.ic_broken_image)
            .placeholder(circularProgressDrawable)
            .into(imageContainer)
    }

    fun rateApp(activity: Activity) {
        val rateIntent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + activity.packageName)
            )
        activity.startActivity(rateIntent)
    }

    fun shareApp(activity: Activity) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            activity.getString(R.string.share_app_message) + "https://play.google.com/store/apps/details?id=" + activity.packageName
        )
        activity.startActivity(shareIntent)

    }

    fun capitalizeFirstLetter(string: String): String {
        val firstLetter = string[0]
        return firstLetter + string.substring(1)
    }

    fun logOut(activity: AppCompatActivity) {
        FirebaseAuth.getInstance().signOut()
        ActivityUtils.startActivity(activity, LoginActivity::class.java)
        activity.finish()
    }

}