package com.example.mindofthevalley.di

import android.app.Application
import android.content.Context
import com.example.mindofthevalley.MindValleyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext appContext: Context): MindValleyApplication {
        return appContext as MindValleyApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

}