package com.example.mindofthevalley.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.mindofthevalley.manager.AppDataManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ManagerModule {

    @Singleton
    @Provides
    fun provideAppDataManager(
        @Named(LAST_CONNECTED_DATA) lastConnectedDataStore: DataStore<Preferences>
    ): AppDataManager = AppDataManager(lastConnectedDataStore)

}