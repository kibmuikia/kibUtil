package kib.project.fast.ui.bottom_bar_screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.core.settings.general.GeneralSettingsManager
import kotlinx.coroutines.launch
import timber.log.Timber

class SettingsScreenViewModel(
    private val generalSettingsManager: GeneralSettingsManager
): ViewModel() {

    init {
        Timber.v(":: vm-init")
    }

    fun setThemeState(value: Int) {
        viewModelScope.launch {
            generalSettingsManager.setThemeIndex(value = value)
        }
    }

    override fun onCleared() {
        Timber.v(":: vm-cleared")
        super.onCleared()
    }
}