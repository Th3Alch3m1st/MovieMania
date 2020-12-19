package com.coderbyte.moviemania.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.WindowManager
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.utils.setSafeOnClickListener
import kotlinx.android.synthetic.main.dialog_no_internet.*

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class NoInternetDialog(context: Context, private val callback: NoInternetDialogCallBack) :
    AlertDialog(context, R.style.CommonDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_no_internet)

        //set full screen
        val manager = context.getSystemService(Activity.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manager.defaultDisplay.getSize(point)
        val width = point.x
        val height = point.y

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window?.attributes)
        lp.width = width
        lp.height = height

        window?.attributes = lp
        setCancelable(false)


        btnTurnOnMobileData.setSafeOnClickListener {
            callback.onMobileDataTurnClick()
        }

        btnTurnOnWifi.setSafeOnClickListener {
            callback.onWifiTurnClick()
        }
    }

    interface NoInternetDialogCallBack {
        fun onRetryBtnClick()
        fun onMobileDataTurnClick()
        fun onWifiTurnClick()
    }
}