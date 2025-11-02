package com.agamy.closeyourmouth.domain.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        val KEY_PHONE = stringPreferencesKey("user_phone")
        val KEY_LOGGED_IN = booleanPreferencesKey("logged_in")
    }


    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[KEY_LOGGED_IN] ?: false }

    val userPhone: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[KEY_PHONE] }



    suspend fun saveUser(phone: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_PHONE] = phone
            prefs[KEY_LOGGED_IN] = true
        }
    }

    /*suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[KEY_PHONE] = null
            prefs[KEY_LOGGED_IN] = false
        }
    }*/
}