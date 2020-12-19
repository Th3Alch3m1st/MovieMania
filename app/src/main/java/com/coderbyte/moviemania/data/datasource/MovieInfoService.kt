package com.coderbyte.moviemania.data.datasource

import com.coderbyte.moviemania.data.model.movie.PopularMoviesResponse
import com.coderbyte.moviemania.data.model.trending.TrendingContentResponse
import com.coderbyte.moviemania.data.model.tvseries.PopularTvSeriesResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
interface MovieInfoService {
    @GET("https://api.themoviedb.org/3/discover/movie")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("primary_release_year") releaseYear: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
    ): Single<Response<PopularMoviesResponse>>

    @GET("https://api.themoviedb.org/3/discover/tv")
    fun getPopularTvSeries(
        @Query("api_key") apiKey: String,
        @Query("primary_release_year") releaseYear: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
    ): Single<Response<PopularTvSeriesResponse>>

    @GET("https://api.themoviedb.org/3/trending/all/week")
    fun getTrendingContent(
        @Query("api_key") apiKey: String,
        @Query("primary_release_year") releaseYear: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
    ): Single<Response<TrendingContentResponse>>
}