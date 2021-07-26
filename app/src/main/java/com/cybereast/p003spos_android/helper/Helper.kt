package com.cybereast.p003spos_android.helper

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.cybereast.p003spos_android.application.POSApplication
import java.io.IOException
import java.util.*

object Helper {

    fun getLocationAddressFromLatAndLong(latitude: Double, longitude: Double): Address? {

        return try {
            val geocoder = Geocoder(POSApplication.getAppContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                latitude,
                longitude, 1
            )
            if (addresses != null && addresses.size > 0) {
                addresses[0]
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (ex: IndexOutOfBoundsException) {
            ex.printStackTrace()
            null
        }
    }

    fun isNetworkConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}