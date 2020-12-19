package com.coderbyte.moviemania.data.datasource

import com.coderbyte.moviemania.data.model.movie.MovieDetails
import com.coderbyte.moviemania.data.model.movie.PopularMoviesResponse
import com.coderbyte.moviemania.data.model.trending.TrendingContentResponse
import com.coderbyte.moviemania.data.model.tvseries.PopularTvSeriesResponse
import com.coderbyte.moviemania.data.model.tvseries.TvSeriesDetails
import com.coderbyte.moviemania.data.network.onResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class MovieInfoAPI @Inject constructor(private val service: MovieInfoService) {
    fun getPopularMovies(
        apiKey: String,
        releaseYear: String,
        sortBy: String,
        page: Int,
    ): Single<PopularMoviesResponse> {
        return service.getPopularMovies(apiKey, releaseYear, sortBy, page)
            .onResponse()
    }

    fun getPopularTvSeries(
        apiKey: String,
        releaseYear: String,
        sortBy: String,
        page: Int,
    ): Single<PopularTvSeriesResponse> {
        return service.getPopularTvSeries(apiKey, releaseYear, sortBy, page)
            .onResponse()
    }

    fun getTrendingContent(
        apiKey: String,
        releaseYear: String,
        sortBy: String,
        page: Int,
    ): Single<TrendingContentResponse> {
        return service.getTrendingContent(apiKey, releaseYear, sortBy, page)
            .onResponse()
    }

    fun getMovieDetails(
        id: String,
        apiKey: String
    ): Single<MovieDetails> {
        return service.getMovieDetails(id, apiKey)
            .onResponse()
    }

    fun getSeriesDetails(
        id: String,
        apiKey: String
    ): Single<TvSeriesDetails> {
        return service.getSeriesDetails(id, apiKey)
            .onResponse()
    }
}