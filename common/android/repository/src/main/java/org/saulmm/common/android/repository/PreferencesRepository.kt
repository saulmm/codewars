package org.saulmm.common.android.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    suspend fun getString(key: Key): String = withContext(Dispatchers.IO) {
        checkNotNull(
            sharedPreferences.getString(key.prefKey, Defaults.DEFAULT_USERNAME)
        )
    }

    suspend fun setString(key: Key, value: String? ) {
        sharedPreferences.edit {
            putString(key.prefKey, value ?: Defaults.DEFAULT_USERNAME)
        }
    }

    enum class Key(val prefKey: String) {
        SELECTED_USERNAME("username.selected")
    }

    private object Defaults {
        const val DEFAULT_USERNAME = "bkaes"
    }
}