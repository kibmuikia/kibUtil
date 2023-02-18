package kib.project.core.settings.general

import kotlinx.coroutines.flow.Flow

interface ConnectivityStatus {
    val isOnline: Flow<Boolean>
    suspend fun setIsOnline(value: Boolean)
}

interface GeneralSettingsManager: ConnectivityStatus