package com.example.mindofthevalley.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

const val baseURL = "baseURL"

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
}