package com.example.mindofthevalley.data

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("channels")
    var channels: ArrayList<Channel>?,

    @SerializedName("categories")
    var categories: ArrayList<Category>?,

    @SerializedName("media")
    var newEpisodes: ArrayList<Course>?,
)

enum class DataType {
    Category, Episodes, Channels, All
}
