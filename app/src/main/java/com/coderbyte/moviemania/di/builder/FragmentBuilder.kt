package com.coderbyte.moviemania.di.builder

import com.coderbyte.moviemania.di.scope.FragmentScope
import com.coderbyte.moviemania.ui.contentdetails.ContentDetailsFragment
import com.coderbyte.moviemania.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun bindHomeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun bindContentDetailsFragment(): ContentDetailsFragment

}