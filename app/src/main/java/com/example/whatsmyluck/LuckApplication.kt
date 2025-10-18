package com.example.whatsmyluck

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.whatsmyluck.data.UserPreferencesRepository

private const val TRACKING_PREFERENCE_NAME = "tracking_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = TRACKING_PREFERENCE_NAME
)

/*
 * Custom app entry point for manual dependency injection
 */
class LuckApplication: Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}