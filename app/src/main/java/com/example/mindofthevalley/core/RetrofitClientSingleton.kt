package com.example.mindofthevalley.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientSingleton {
    private const val BASE = "https://pastebin.com/raw/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}