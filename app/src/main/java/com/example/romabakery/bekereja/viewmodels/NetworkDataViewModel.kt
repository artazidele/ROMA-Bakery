package com.example.romabakery.bekereja.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel

enum class NetworkDataStatus { LOADING, ERROR, DONE }

class NetworkDataViewModel : ViewModel() {
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