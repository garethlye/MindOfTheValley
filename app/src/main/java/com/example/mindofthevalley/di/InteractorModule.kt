package com.example.mindofthevalley.di

import android.content.Context
import com.example.mindofthevalley.interactor.ChannelsInteractor
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
    ): ChannelsInteractor =
        ChannelsInteractor(channelsAPI)

}