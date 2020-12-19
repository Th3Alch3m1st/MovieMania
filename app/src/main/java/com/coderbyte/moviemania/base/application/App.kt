package com.coderbyte.moviemania.base.application

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.coderbyte.moviemania.BuildConfig
import com.coderbyte.moviemania.di.component.DaggerAppComponent
import com.facebook.stetho.Stetho
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class App : MultiDexApplication(), HasAndroidInjector, LifecycleObserver {

    companion object {

        @get:Synchronized
        lateinit var instance: App

        fun getApplication(activity: Activity): App {
            return activity.application as App
        }

        fun get(context: Context): App {
            return context.applicationContext as App
        }
    }


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    private var inBackground = false

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        // SSL Security Provider comes with google lay service
        //ProviderInstaller.installIfNeeded(this)
        RxJavaPlugins.setErrorHandler {
            Log.e("error", "Rx Global Error Handler: " + it.localizedMessage)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        Stetho.initializeWithDefaults(this)
        //AppSignatureHelper(this).appSignatures
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return activityInjector
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Log.d("App", "App in foreground")
        inBackground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Log.d("App", "App in background")
        inBackground = true
    }
}