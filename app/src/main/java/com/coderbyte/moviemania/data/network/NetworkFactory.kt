package com.coderbyte.moviemania.data.network

import android.content.Context
import android.os.Build
import android.util.Log
import com.coderbyte.moviemania.BuildConfig
import com.coderbyte.moviemania.data.session.AppPreference
import com.coderbyte.moviemania.data.session.Session
import com.coderbyte.moviemania.utils.Tls12SocketFactory
import com.coderbyte.moviemania.utils.isConnected
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.*
import okhttp3.internal.platform.Platform
import org.jetbrains.anko.defaultSharedPreferences
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object NetworkFactory {

    private const val BASE_URL = BuildConfig.BASE_URL
    private const val TIME_OUT = 40L

    fun <Service> createService(
        appContext: Context,
        serviceClass: Class<Service>,
        isDebugBuild: Boolean
    ): Service {
        return getRetrofit(
            BASE_URL,
            getOkHttpClient(
                getAuthInterceptor(appContext),
                getLogInterceptors(isDebugBuild),
                appContext
            )
        ).create(serviceClass)
    }

    fun getRetrofit(appContext: Context): Retrofit {
        return getRetrofit(
            okHttpClient = getOkHttpClient(
                getAuthInterceptor(appContext),
                getLogInterceptors(true),
                appContext
            )
        )
    }

    private fun getRetrofit(baseUrl: String = BASE_URL, okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }


    private fun getOkHttpClient(
        authInterceptor: Interceptor,
        logInterceptor: Interceptor,
        context: Context
    ): OkHttpClient {
        var clientBuilder = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(authInterceptor)
            .cache(getCache(context))
            .addNetworkInterceptor(StethoInterceptor())
            .retryOnConnectionFailure(true)
        clientBuilder = enableTls12OnPreLollipop(clientBuilder)

        return clientBuilder.build()

    }

    fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT < 22) {
            try {
                val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
                val sc: SSLContext = SSLContext.getInstance("SSL")
                sc.init(null, trustAllCerts, SecureRandom())
                client.sslSocketFactory(
                    Tls12SocketFactory(sc.socketFactory),
                    trustAllCerts[0] as X509TrustManager
                )
                val cs: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build()
                val csslv3: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.SSL_3_0)
                    .build()
                val specs: MutableList<ConnectionSpec> = ArrayList()
                specs.add(cs)
                specs.add(csslv3)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)
                client.connectionSpecs(specs)
            } catch (exc: Exception) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
            }
        }
        return client
    }


    private fun getAuthInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            if(isConnected(context)){
                val maxAge = 60 // read from cache for 1 minute
                requestBuilder.addHeader("Cache-Control", "public, max-age=$maxAge")
            }else {
                requestBuilder.addHeader(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=$maxStale"
                )
                    .removeHeader("Pragma")
            }

            chain.proceed(requestBuilder.build())
        }
    }

    private fun getLogInterceptors(debuggable: Boolean): Interceptor {
        return LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .tag("MovieMania")
            .request("MovieRequest")
            .response("MovieResponse")
            .executor(Executors.newSingleThreadExecutor())
            .build()
    }

    private fun getCache(context: Context): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
        return Cache(context.applicationContext.cacheDir, cacheSize)
    }

    private fun getSharedPreference(appContext: Context): Session {
        return AppPreference(appContext.defaultSharedPreferences)
    }
}