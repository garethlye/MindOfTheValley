package com.example.mindofthevalley.interactor

import com.example.mindofthevalley.manager.AppDataManager
import com.example.mindofthevalley.network.ChannelsAPI
import com.example.mindofthevalley.network.ChannelsApiResponse

class ChannelsInteractor(val appDataManager: AppDataManager,
                         private val channelsAPI: ChannelsAPI) {

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