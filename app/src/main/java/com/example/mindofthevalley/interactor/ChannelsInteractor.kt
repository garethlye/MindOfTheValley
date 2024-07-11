package com.example.mindofthevalley.interactor

import android.content.Context
import com.example.mindofthevalley.network.ChannelsAPI
import com.example.mindofthevalley.network.ChannelsApiResponse

class ChannelsInteractor(private val channelsAPI: ChannelsAPI) {

    suspend fun getEpisodes(): ChannelsApiResponse {
        return channelsAPI.getEpisodes()
    }

    suspend fun getChannels(): ChannelsApiResponse {
        return channelsAPI.getChannels()
    }

    suspend fun getCategories(): ChannelsApiResponse {
        return channelsAPI.getCategories()
    }

}