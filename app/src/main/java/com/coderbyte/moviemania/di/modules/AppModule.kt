package com.coderbyte.moviemania.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.coderbyte.moviemania.data.session.Session
import com.coderbyte.moviemania.data.session.AppPreference
import com.coderbyte.moviemania.di.qualifire.ApplicationContext
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindApplicationContext(application: Application): Context

    @Binds
    abstract fun bindSession(preferences: AppPreference): Session

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.defaultSharedPreferences
        }
    }

}