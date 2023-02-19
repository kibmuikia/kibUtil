package kib.project.core.settings.general

import kotlinx.coroutines.flow.Flow

interface ConnectivityStatus {
    val isOnline: Flow<Boolean>
    suspend fun setIsOnline(value: Boolean)
}

interface UserStatus {
    val isFirstVisit: Flow<Boolean>
    suspend fun setIsFirstVisit(value: Boolean)
}

interface GeneralSettingsManager :
    ConnectivityStatus,
    UserStatus