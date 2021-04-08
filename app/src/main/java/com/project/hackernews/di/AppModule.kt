package com.project.hackernews.di

import android.content.Context
import com.project.hackernews.data.backend.BackendService
import com.project.hackernews.data.database.AppDatabase
import com.project.hackernews.data.database.NewsEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideNewsEntityDao(appDatabase: AppDatabase): NewsEntityDao {
        return appDatabase.newsEntityDao()
    }

    @Singleton
    @Provides
    fun provideBackendService(): BackendService {
        return BackendService.create()
    }
}