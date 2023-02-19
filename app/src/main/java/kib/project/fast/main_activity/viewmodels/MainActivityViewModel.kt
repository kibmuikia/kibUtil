package kib.project.fast.main_activity.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.core.settings.general.GeneralSettingsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class MainActivityViewModel(
    private val generalSettingsManager: GeneralSettingsManager
): ViewModel() {

    val isOnline = generalSettingsManager.isOnline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    init {
        Timber.v(":: triggered")
    }

    override fun onCleared() {
        Timber.v(":: triggered")
        super.onCleared()
    }

}