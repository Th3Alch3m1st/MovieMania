package com.coderbyte.moviemania.di.component

import android.app.Application
import com.coderbyte.moviemania.base.application.App
import com.coderbyte.moviemania.di.builder.ActivityBuilder
import com.coderbyte.moviemania.di.builder.FragmentBuilder
import com.coderbyte.moviemania.di.modules.AppModule
import com.coderbyte.moviemania.di.modules.DataSourceModule
import com.coderbyte.moviemania.di.modules.RepositoryModule
import com.coderbyte.moviemania.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class,
        DataSourceModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}