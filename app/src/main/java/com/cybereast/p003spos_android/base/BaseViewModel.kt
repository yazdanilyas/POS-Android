package com.cybereast.p003spos_android.base


import androidx.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel() {
//    val mDataManager = DataManager

//    fun setServerName(textView: TextView) {
//        if (!BuildConfig.FLAVOR.equals(Constants.PRODUCTION)) {
//            textView.visibility = View.VISIBLE
//            textView.text = BuildConfig.FLAVOR.toUpperCase()
//        } else {
//            textView.visibility = View.GONE
//        }
//    }

//    fun cancelServerRequest() {
//        DisposableManager.dispose()
//    }
//
//    fun deleteAllLocalData() {
//
//    }

//    fun createSession(pContext: Context, authModel: AuthModel) {
//        PrefUtils.setBoolean(pContext, KEY_IS_LOGGED_IN, true)
//
//        PrefUtils.setString(pContext, KEY_JWT, authModel.jwt)
//        PrefUtils.setString(pContext, KEY_FCM_TOKEN, authModel.fcmToken)
//        PrefUtils.setString(pContext, KEY_FIRST_NAME, authModel.firstName)
//        PrefUtils.setString(pContext, KEY_LAST_NAME, authModel.lastName)
//
//        PrefUtils.setString(pContext, KEY_SELLER_STATUS, authModel.sellerStatus)
//        PrefUtils.setString(pContext, KEY_SELLER_STATUS_MESSAGE, authModel.sellerStatusMessage)
//        PrefUtils.setInt(pContext, KEY_TOTAL_EARNING, authModel.totalEarnings ?: 0)
//
//        PrefUtils.setString(pContext, KEY_EMAIL, authModel.email)
//        PrefUtils.setString(pContext, KEY_PROFILE_PIC, authModel.avatar)
//        PrefUtils.setString(pContext, KEY_PHONE_NUMBER, authModel.phoneNumber)
//    }
//

//
//    fun isLogin(): Boolean {
//        return mDataManager.isLogin()
//    }

}