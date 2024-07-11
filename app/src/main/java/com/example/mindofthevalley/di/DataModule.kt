package com.example.mindofthevalley.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

const val baseURL = "baseURL"
const val LAST_CONNECTED_DATA = "LAST_CONNECTED_DATA"

private val Context.lastConnectedData by preferencesDataStore(name = LAST_CONNECTED_DATA)
@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    @Named(LAST_CONNECTED_DATA)
    fun provideLastConnectedDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.lastConnectedData
    }

}