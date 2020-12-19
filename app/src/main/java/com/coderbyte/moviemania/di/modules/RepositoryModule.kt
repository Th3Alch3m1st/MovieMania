package com.coderbyte.moviemania.di.modules


import com.coderbyte.moviemania.data.repository.MovieInfoRepository
import com.coderbyte.moviemania.data.repository.MovieInfoRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideHomeRepository(repository: MovieInfoRepositoryImpl): MovieInfoRepository
}