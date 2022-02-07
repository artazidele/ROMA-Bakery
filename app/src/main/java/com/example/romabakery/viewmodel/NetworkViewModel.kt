package com.example.romabakery.viewmodel

import android.content.Context
import android.net.ConnectivityManager

enum class NetworkLoadingStatus { LOADING, ERROR, DONE }

class NetworkViewModel {
    private fun checkConnectionType(context: Context): Boolean {
        val connectionManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi_Connection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile_data_connection =
            connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifi_Connection!!.isConnectedOrConnecting) {
            return true
        } else {
            if (mobile_data_connection!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }
}
