package com.example.mindofthevalley.data

import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("type")
    val type: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("coverAsset")
    val coverAsset: CoverAsset?,

    @SerializedName("channel")
    val channelMini: ChannelMini?,
)