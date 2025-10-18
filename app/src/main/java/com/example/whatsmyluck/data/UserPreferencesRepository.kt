package com.example.whatsmyluck.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val IS_TRACKING_QUESTIONS = booleanPreferencesKey("is_tracking_questions")
        val NUM_QUESTIONS_TO_ASK = doublePreferencesKey("num_questions_to_ask")
        const val TAG = "UserPreferencesRepo"
    }

    val isTrackingQuestions: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_TRACKING_QUESTIONS] ?: false
        }

    val numQuestionsToAsk: Flow<Double> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[NUM_QUESTIONS_TO_ASK] ?: Double.POSITIVE_INFINITY
        }

    suspend fun saveTrackingPreference(isTrackingQuestions: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_TRACKING_QUESTIONS] = isTrackingQuestions
        }
    }

    suspend fun saveNumQuestionsPreference(numQuestionsToAsk: Double) {
        dataStore.edit { preferences ->
            preferences[NUM_QUESTIONS_TO_ASK] = numQuestionsToAsk
        }
    }
}