package com.example.mindofthevalley.data

import com.google.gson.annotations.SerializedName

data class Series(
    @SerializedName("title")
    val title: String?,

    @SerializedName("coverAsset")
    val coverAsset: CoverAsset?,
)