package com.example.data_layer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * This extension function allows any entity hosting a [Context] to easily check whether there is a
 * valid data connection. It also takes into account the Android version available.
 *
 * @author Julio Navarro Anguita
 * @since 1.0
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}
