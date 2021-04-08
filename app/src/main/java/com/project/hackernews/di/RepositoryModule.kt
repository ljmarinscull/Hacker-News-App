package com.project.hackernews.di

import com.project.hackernews.data.backend.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideIRepository(
        repository: NewsRepository
    ): IRepository


}