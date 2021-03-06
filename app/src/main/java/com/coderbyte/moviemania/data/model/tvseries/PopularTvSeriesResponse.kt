package com.coderbyte.moviemania.data.model.tvseries

import com.google.gson.annotations.SerializedName

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
data class PopularTvSeriesResponse(
    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null,

    @field:SerializedName("results")
    val popularTvSeries: MutableList<PopularTvSeries>? = null,
)