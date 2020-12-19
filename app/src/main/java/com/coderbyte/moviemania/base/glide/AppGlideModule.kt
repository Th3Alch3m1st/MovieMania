package com.coderbyte.moviemania.base.glide

import android.content.Context
import com.coderbyte.moviemania.data.network.NetworkFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
@GlideModule
class AppGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val builder = NetworkFactory.enableTls12OnPreLollipop(OkHttpClient.Builder())
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(builder.build())
        )
    }
}
