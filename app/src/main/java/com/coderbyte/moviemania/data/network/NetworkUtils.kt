package com.coderbyte.moviemania.data.network

import android.content.Context
import android.util.Log
import io.reactivex.Single
import retrofit2.Response

fun <T> Single<Response<T>>.onResponse(): Single<T> {
    return this.map { response ->
        if(response.isSuccessful) {
            if(response.body()!=null) {
                response.body()
            } else {
                throw ApiException(response.code().toString(), null, response.message())
            }
        } else {
            throw ApiException(response.code().toString(), response.errorBody(), response.message())
        }
    }
}

fun <T> Single<T>.onException(appContext: Context): Single<T> {
    return this.onErrorResumeNext {
        Single.create<T> { emitter ->
            if (it is ApiException) {
                emitter.onError(ErrorHandler.parseRequestException(appContext, it.status, it.errorBody, it.message))
            } else {
                Log.e("error",it.message ?: " Unknnown ex")
                emitter.onError(ErrorHandler.parseIOException(appContext))
            }

        }
    }
}

