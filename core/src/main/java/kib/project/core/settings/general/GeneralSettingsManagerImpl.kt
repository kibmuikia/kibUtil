package kib.project.core.settings.general

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore by preferencesDataStore("general_settings_ref")

class GeneralSettingsManagerImpl(context: Context): GeneralSettingsManager {
    private val dataStore = context.dataStore

    override val isOnline: Flow<Boolean>
        get() = getBooleanPreferenceItem(IS_NETWORK_CONNECTED)

    override suspend fun setIsOnline(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_NETWORK_CONNECTED] = value
        }
    }

    private fun getBooleanPreferenceItem(key: Preferences.Key<Boolean>) = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            preference[key] ?: false
        }

    companion object {
        /**
         * Holds the current network state
         * */
        val IS_NETWORK_CONNECTED = booleanPreferencesKey("is_network_connected")
    }
}