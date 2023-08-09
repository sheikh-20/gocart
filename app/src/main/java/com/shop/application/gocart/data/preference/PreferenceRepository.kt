package com.shop.application.gocart.data.preference

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import java.io.IOException
import javax.inject.Inject

interface PreferenceRepository {
    val getPreferenceFlow: Flow<Any>
    suspend fun storePreference(value: Any)
}

class TokenRepositoryImpl constructor(private val datastore: DataStore<Preferences>): PreferenceRepository {

    private companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        const val TAG = "PreferenceRepo"
    }

    override val getPreferenceFlow: Flow<Any> = datastore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[AUTH_TOKEN] ?: ""
        }

    override suspend fun storePreference(value: Any) {
        datastore.edit { preferences ->
            preferences[AUTH_TOKEN] = value.toString()
        }
    }
}