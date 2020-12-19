package com.coderbyte.moviemania.ui

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.base.activity.BaseActivity
import com.coderbyte.moviemania.base.viewmodel.BaseViewModel
import com.coderbyte.moviemania.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class MainActivity :BaseActivity<BaseViewModel,ActivityMainBinding>(){
    private lateinit var navController: NavController
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun setVariables(dataBinding: ActivityMainBinding) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        // navigation ui for handling fragment label and up as action button
        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(toolbar, navController)

        Log.e("error","MainActivity")
    }
}