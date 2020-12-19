package com.coderbyte.moviemania.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */

fun isConnected(appContext: Context): Boolean {
    val connectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}