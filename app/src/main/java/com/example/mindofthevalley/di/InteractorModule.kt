package com.example.mindofthevalley.di

import com.example.mindofthevalley.interactor.ChannelsInteractor
import com.example.mindofthevalley.manager.AppDataManager
import com.example.mindofthevalley.network.ChannelsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object InteractorModule {

    @Provides
    fun provideUserInteractor(
        channelsAPI: ChannelsAPI,
        appDataManager: AppDataManager
    ): ChannelsInteractor =
        ChannelsInteractor(appDataManager, channelsAPI)

}