package com.coderbyte.moviemania.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
data class WishListItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,
)