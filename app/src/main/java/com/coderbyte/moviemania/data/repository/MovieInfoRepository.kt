package com.coderbyte.moviemania.data.repository

import com.coderbyte.moviemania.data.model.movie.MovieDetails
import com.coderbyte.moviemania.data.model.movie.PopularMoviesResponse
import com.coderbyte.moviemania.data.model.trending.TrendingContentResponse
import com.coderbyte.moviemania.data.model.tvseries.PopularTvSeriesResponse
import com.coderbyte.moviemania.data.model.tvseries.TvSeriesDetails
import io.reactivex.Single

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
interface MovieInfoRepository {
    fun getPopularMovies(page: Int): Single<PopularMoviesResponse>
    fun getPopularTvSeries(page: Int): Single<PopularTvSeriesResponse>
    fun getTrendingContent(page: Int): Single<TrendingContentResponse>
    fun getMovieDetails(id: String): Single<MovieDetails>
    fun getSeriesDetails(id: String): Single<TvSeriesDetails>
}