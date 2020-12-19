package com.coderbyte.moviemania.di.builder

import com.coderbyte.moviemania.di.scope.ActivityScope
import com.coderbyte.moviemania.ui.MainActivity
import com.coderbyte.moviemania.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}