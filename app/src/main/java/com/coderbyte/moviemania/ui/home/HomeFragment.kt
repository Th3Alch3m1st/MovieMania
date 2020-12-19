package com.coderbyte.moviemania.ui.home

import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.base.fragment.BaseFragment
import com.coderbyte.moviemania.base.viewmodel.BaseViewModel
import com.coderbyte.moviemania.databinding.FragmentHomeBinding

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class HomeFragment:BaseFragment<HomeViewModel,FragmentHomeBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override fun setVariables(dataBinding: FragmentHomeBinding) {

    }
}