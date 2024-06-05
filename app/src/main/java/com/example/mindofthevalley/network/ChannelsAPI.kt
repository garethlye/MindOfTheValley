package com.example.mindofthevalley.network

import retrofit2.http.GET

interface ChannelsAPI {

    companion object {
        const val methodEpisodes = "z5AExTtw"
        const val methodGetChannels = "Xt12uVhM"
        const val methodGetCategory = "A0CgArX3"
    }

    @GET(methodEpisodes)
    suspend fun getEpisodes(): ChannelsApiResponse

    @GET(methodGetChannels)
    suspend fun getChannels(): ChannelsApiResponse

    @GET(methodGetCategory)
    suspend fun getCategories(): ChannelsApiResponse

}