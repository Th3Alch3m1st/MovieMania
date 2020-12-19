package com.coderbyte.moviemania.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.base.activity.BaseActivity
import com.coderbyte.moviemania.base.viewmodel.BaseViewModel
import com.coderbyte.moviemania.databinding.ActivitySplashBinding
import com.coderbyte.moviemania.ui.MainActivity


class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {
    companion object {
        const val TAG = "SplashActivity"
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_splash

    override fun setVariables(dataBinding: ActivitySplashBinding) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
//            startActivity(MainActivity::class.java, true)
//            Log.e("error","SplashActivity")
        },1000)
    }
}