package com.example.mindofthevalley.network

import com.example.mindofthevalley.data.Data
import com.google.gson.annotations.SerializedName

data class ChannelsApiResponse(
    @SerializedName("data")
    val data: Data?,
)
