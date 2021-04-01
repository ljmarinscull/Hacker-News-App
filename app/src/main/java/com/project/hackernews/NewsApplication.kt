package com.project.hackernews

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.preference.PreferenceManager.getDefaultSharedPreferences
import com.project.hackernews.data.backend.Backend
import java.util.*

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Backend.init(applicationContext)
    }

    companion object {
        fun getNewsDeletedId(context: Context): Set<String> {
            val mPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), MODE_PRIVATE)
            return mPreferences.getStringSet("news_deleted_ids", HashSet())!!
        }

        fun saveNewsDeletedId(context: Context, id: String) {
            val mPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), MODE_PRIVATE)
            val editor = mPreferences.edit()
            val list = HashSet(mPreferences.getStringSet("news_deleted_ids", HashSet()))
            list.add(id)
            editor.putStringSet("news_deleted_ids", list)
            editor.apply()
        }
    }
}