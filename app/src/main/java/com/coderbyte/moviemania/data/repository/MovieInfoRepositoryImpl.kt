package com.coderbyte.moviemania.data.repository

import android.content.Context
import com.coderbyte.moviemania.BuildConfig
import com.coderbyte.moviemania.data.datasource.MovieInfoAPI
import com.coderbyte.moviemania.data.model.movie.PopularMoviesResponse
import com.coderbyte.moviemania.data.model.trending.TrendingContentResponse
import com.coderbyte.moviemania.data.model.tvseries.PopularTvSeriesResponse
import com.coderbyte.moviemania.data.network.onException
import com.coderbyte.moviemania.data.session.Session
import com.coderbyte.moviemania.di.qualifire.ApplicationContext
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class MovieInfoRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: MovieInfoAPI,
    private val session: Session
) : MovieInfoRepository {
    override fun getPopularMovies(
        page: Int
    ): Single<PopularMoviesResponse> {
        return api.getPopularMovies(BuildConfig.API_KEY, "2020", "vote_average.desc", page)
            .onException(context)
    }

    override fun getPopularTvSeries(page: Int): Single<PopularTvSeriesResponse> {
        return api.getPopularTvSeries(BuildConfig.API_KEY, "2020", "vote_average.desc", page)
            .onException(context)
    }

    override fun getTrendingContent(page: Int): Single<TrendingContentResponse> {
        return api.getTrendingContent(BuildConfig.API_KEY, "2020", "vote_average.desc", page)
            .onException(context)
    }
}