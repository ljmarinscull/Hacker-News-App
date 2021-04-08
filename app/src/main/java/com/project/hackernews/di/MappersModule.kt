package com.project.hackernews.di

import com.project.hackernews.data.mappers.IMapper
import com.project.hackernews.data.mappers.NewsDataMapper
import com.project.hackernews.data.model.NewObject
import com.project.hackernews.data.model.NewsEntity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    abstract fun provideIMapper(
        mapper: NewsDataMapper
    ): IMapper<NewObject, NewsEntity>

}