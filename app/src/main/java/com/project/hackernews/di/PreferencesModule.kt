package com.project.hackernews.di

import com.project.hackernews.data.preferences.AppPreferences
import com.project.hackernews.data.preferences.IPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun provideIPreferences(
        appPreference: AppPreferences
    ): IPreferences
}