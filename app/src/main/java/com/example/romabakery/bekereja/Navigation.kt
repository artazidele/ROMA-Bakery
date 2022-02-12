package com.example.romabakery.bekereja

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class Navigation {
    public fun fromTo(fromContext: Context, toContext: Context) {
        val intent = Intent(fromContext!!, toContext::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        ContextCompat.startActivity(fromContext!!, intent, null)
    }
//    public fun logOut(fromContext: Context, toContext: Context) {
//        // Jāiziet no Firebase
//        val intent = Intent(fromContext!!, toContext::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        ContextCompat.startActivity(fromContext!!, intent, null)
//    }

}