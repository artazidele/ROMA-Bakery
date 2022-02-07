package com.example.romabakery.viewmodel

import android.content.Context
import android.net.ConnectivityManager

enum class NetworkLoadingStatus { LOADING, ERROR, DONE }

class NetworkViewModel {
    public fun checkConnection(context: Context): Boolean {
        var isConnection = false
        val connectionManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileDataConnection =
            connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiConnection!!.isConnectedOrConnecting || mobileDataConnection!!.isConnectedOrConnecting) {
            isConnection = true
        }
        return isConnection
    }
}
