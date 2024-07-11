package com.example.mindofthevalley.di

import com.example.mindofthevalley.network.ChannelsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    fun provideChannelsAPI(retrofit: Retrofit): ChannelsAPI {
        return retrofit.create(ChannelsAPI::class.java)
    }

}