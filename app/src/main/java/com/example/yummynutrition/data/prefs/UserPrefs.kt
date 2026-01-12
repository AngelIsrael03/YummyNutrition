package com.example.yummynutrition.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "yummy_prefs"

val Context.userDataStore by preferencesDataStore(name = DATASTORE_NAME)

object UserPrefs {
    private val KEY_NAME = stringPreferencesKey("user_name")

    fun nameFlow(context: Context): Flow<String> =
        context.userDataStore.data.map { prefs -> prefs[KEY_NAME].orEmpty() }

    suspend fun setName(context: Context, name: String) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_NAME] = name
        }
    }
}
