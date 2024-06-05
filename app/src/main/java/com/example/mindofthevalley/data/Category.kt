package com.example.mindofthevalley.data

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("name")
    val categoryName: String?,
)
