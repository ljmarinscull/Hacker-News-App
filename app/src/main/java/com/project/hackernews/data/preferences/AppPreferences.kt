package com.project.hackernews.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.*
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AppPrefStorage")

class AppPreferences @Inject constructor(
    @ApplicationContext val context: Context) : IPreferences {

    // since @Singleton scope is used, dataStore will have the same instance every time
    private val dataStore = context.dataStore


    private object PreferencesKeys {
        val NEWS_DELETED_IDS = stringSetPreferencesKey("news_deleted_ids")
    }

    override val newsDeletedIds: Flow<Set<String>>
        get() =  dataStore.getValueAsFlow(PreferencesKeys.NEWS_DELETED_IDS, HashSet())

    override suspend fun saveNewsDeletedId(id: String) {
        dataStore.edit { settings ->
            val currentvalue = HashSet(settings[PreferencesKeys.NEWS_DELETED_IDS] ?: HashSet())
            currentvalue.add(id)
            settings[PreferencesKeys.NEWS_DELETED_IDS] = currentvalue
        }
    }

    /***
     * handy function to save key-value pairs in Preference. Sets or updates the value in Preference
     * @param key used to identify the preference
     * @param value the value to be saved in the preference
     */
    private suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        this.edit { preferences ->
            // save the value in prefs
            preferences[key] = value
        }
    }

    /***
     * handy function to return Preference value based on the Preference key
     * @param key  used to identify the preference
     * @param defaultValue value in case the Preference does not exists
     * @throws Exception if there is some error in getting the value
     * @return [Flow] of [T]
     */
    private fun <T> DataStore<Preferences>.getValueAsFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return this.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                // we try again to store the value in the map operator
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // return the default value if it doesn't exist in the storage
            preferences[key] ?: defaultValue
        }
    }

}