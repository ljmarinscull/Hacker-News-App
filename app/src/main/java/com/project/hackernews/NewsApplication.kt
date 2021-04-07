package com.project.hackernews

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.project.hackernews.data.backend.Backend
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Backend.init(applicationContext)
    }
}