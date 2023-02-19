package kib.project.fast.ui.bottom_bar_screens.settings

import androidx.lifecycle.ViewModel
import kib.project.core.settings.general.GeneralSettingsManager
import timber.log.Timber

class SplashScreenViewModel(
    private val generalSettingsManager: GeneralSettingsManager,
) : ViewModel() {

    init {
        Timber.i(":: triggered")
    }

}