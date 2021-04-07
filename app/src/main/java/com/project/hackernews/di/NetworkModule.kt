package com.project.hackernews.di

import com.project.hackernews.data.backend.BackendService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideBackendService(): BackendService {
        return BackendService.create()
    }
}