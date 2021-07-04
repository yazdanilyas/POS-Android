package com.cybereast.p003spos_android.application

import android.app.Application
import android.content.Context

class POSApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
//        FirebaseApp.initializeApp(this)
    }

    companion object {
        private lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }
    }
}