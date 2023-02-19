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

class GeneralSettingsManagerImpl(context: Context) : GeneralSettingsManager {
    private val dataStore = context.dataStore

    override val isOnline: Flow<Boolean>
        get() = getBooleanPreferenceItem(IS_NETWORK_CONNECTED)

    override suspend fun setIsOnline(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_NETWORK_CONNECTED] = value
        }
    }

    override val isFirstVisit: Flow<Boolean>
        get() = getBooleanPreferenceItemDefaultTrue(IS_USER_FIRST_VISIT)

    override suspend fun setIsFirstVisit(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_USER_FIRST_VISIT] = value
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

    private fun getBooleanPreferenceItemDefaultTrue(key: Preferences.Key<Boolean>) = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            preference[key] ?: true
        }

    companion object {
        /**
         * Holds the current network state
         * */
        val IS_NETWORK_CONNECTED = booleanPreferencesKey("is_network_connected")

        /**
         * Holds status of user visit to the app
         * */
        val IS_USER_FIRST_VISIT = booleanPreferencesKey("is_user_first_visit")
    }
}