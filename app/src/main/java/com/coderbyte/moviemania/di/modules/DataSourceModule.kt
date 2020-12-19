package com.coderbyte.moviemania.di.modules

import android.content.Context
import com.coderbyte.moviemania.BuildConfig
import com.coderbyte.moviemania.data.datasource.MovieInfoService
import com.coderbyte.moviemania.data.network.NetworkFactory
import com.coderbyte.moviemania.di.qualifire.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataSourceModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideHomeService(@ApplicationContext context: Context): MovieInfoService =
        NetworkFactory.createService(context, MovieInfoService::class.java, BuildConfig.DEBUG)
}