package com.coderbyte.moviemania.utils

import android.os.SystemClock
import android.view.View

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */

class SafeClickListener(
    private var defaultInterval: Int = 2000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}