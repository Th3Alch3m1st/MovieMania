package com.coderbyte.moviemania.data.network

import com.google.gson.annotations.SerializedName

data class Error(

    @SerializedName("code")
    val code: String = "",

    @SerializedName("hint")
    val hint: String = "",

    @SerializedName("type")
    val type: String = "",

    @SerializedName("message")
    val message: String = "",

    @SerializedName("target")
    val target: String = ""
)