package kib.project.fast.ui.bottom_bar_screens.settings

import androidx.lifecycle.ViewModel
import kib.project.core.settings.general.GeneralSettingsManager
import timber.log.Timber

class SettingsScreenViewModel(
    private val generalSettingsManager: GeneralSettingsManager
): ViewModel() {

    init {
        Timber.v(":: vm-init")
    }

    override fun onCleared() {
        Timber.v(":: vm-cleared")
        super.onCleared()
    }
}