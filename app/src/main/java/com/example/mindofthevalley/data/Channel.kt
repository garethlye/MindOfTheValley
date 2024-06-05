package com.example.mindofthevalley.data

import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("title")
    val title: String?,
    @SerializedName("series")
    val series: List<Series>?,
    @SerializedName("mediaCount")
    val mediaCount: Int?,
    @SerializedName("latestMedia")
    val courses: List<Course>?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("iconAsset")
    val iconAsset: IconAsset?,
    @SerializedName("coverAsset")
    val coverAsset: CoverAsset?,
)
