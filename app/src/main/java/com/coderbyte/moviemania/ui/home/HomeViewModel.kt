package com.coderbyte.moviemania.ui.home

import androidx.lifecycle.MutableLiveData
import com.coderbyte.moviemania.base.viewmodel.BaseViewModel
import com.coderbyte.moviemania.data.model.movie.PopularMoviesResponse
import com.coderbyte.moviemania.data.model.trending.TrendingContentResponse
import com.coderbyte.moviemania.data.model.tvseries.PopularTvSeries
import com.coderbyte.moviemania.data.model.tvseries.PopularTvSeriesResponse
import com.coderbyte.moviemania.data.repository.MovieInfoRepository
import com.coderbyte.moviemania.utils.withScheduler
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
class HomeViewModel @Inject constructor(private val movieRepository: MovieInfoRepository) :
    BaseViewModel() {
    var request = 0
    var response = 0
    val hideProgressbar = MutableLiveData<Boolean>()
    val popularMoviesResponse=MutableLiveData<PopularMoviesResponse>()
    val popularTvSeriesResponse=MutableLiveData<PopularTvSeriesResponse>()
    val popularTrendingResponse=MutableLiveData<TrendingContentResponse>()

    fun getPopularMovies(page:Int) {
        val disposable = movieRepository.getPopularMovies(page)
            .withScheduler()
            .doOnSubscribe { ++request }
            .doAfterTerminate {
                ++response
                if (request == response) {
                    hideProgressbar.value = true
                }
            }.subscribe({
                popularMoviesResponse.value = it
            }, {
                toastMessage.value = it.message ?: "Something went Wrong"
            })
        compositeDisposable.add(disposable)
    }

    fun getPopularTvSeries(page:Int) {
        val disposable = movieRepository.getPopularTvSeries(page)
            .withScheduler()
            .doOnSubscribe { ++request }
            .doAfterTerminate {
                ++response
                if (request == response) {
                    hideProgressbar.value = true
                }
            }.subscribe({
                popularTvSeriesResponse.value = it
            }, {
                toastMessage.value = it.message ?: "Something went Wrong"
            })
        compositeDisposable.add(disposable)
    }

    fun getTrending(page:Int) {
        val disposable = movieRepository.getTrendingContent(page)
            .withScheduler()
            .doOnSubscribe { ++request }
            .doAfterTerminate {
                ++response
                if (request == response) {
                    hideProgressbar.value = true
                }
            }.subscribe({
                popularTrendingResponse.value = it
            }, {
                toastMessage.value = it.message ?: "Something went Wrong"
            })
        compositeDisposable.add(disposable)
    }
}