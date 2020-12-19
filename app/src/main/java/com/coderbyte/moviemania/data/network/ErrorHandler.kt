package com.coderbyte.moviemania.data.network

import android.content.Context
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.utils.isConnected
import okhttp3.ResponseBody

object ErrorHandler {
    /**
     * parse [RequestException] from IO exception returned from [okhttp3.OkHttpClient] and [ApolloClient]
     */
    fun parseIOException(appContext: Context): RequestException {
        return if (!isConnected(appContext)) {
            RequestException(message = appContext.getString(R.string.msg_no_connection))
        } else {
            RequestException(message = appContext.getString(R.string.msg_unknown_exception))
        }
    }

    /**
     * parse [RequestException] from [okhttp3.OkHttpClient] response
     */
    fun parseRequestException(appContext: Context, status: String, errorBody: ResponseBody? = null, message: String? = null): RequestException {
        errorBody?.let { body ->
            // parse error model from response
            val apiError: ApiError = getConverter(appContext, ApiError::class.java, body)

            // if error response does not contain any specific message use a generic error message from resource
            return RequestException().apply {
                this.message = apiError.error?.message ?: appContext.getString(R.string.msg_failed_try_again)
                this.status = status
                this.statusCode = apiError.statusCode
            }
        }

        message?.let {msg ->
            return RequestException(message = msg)
        }

        return RequestException(message = appContext.getString(R.string.msg_failed_try_again))
    }

    /**
     * Convert error response to a data class
     */
    private fun <T> getConverter(appContext: Context, clazz: Class<T>, errorBody: ResponseBody): T {
        return try {
            NetworkFactory.getRetrofit(appContext)
                .responseBodyConverter<T>(clazz, arrayOfNulls<Annotation>(0))
                .convert(errorBody)?.let { it } ?: throw Exception(appContext.getString(R.string.msg_failed_try_again))
        } catch (ex: Exception) {
            clazz.newInstance()
        }
    }
}