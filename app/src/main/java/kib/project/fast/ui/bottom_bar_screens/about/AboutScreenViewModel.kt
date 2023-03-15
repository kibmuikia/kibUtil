package kib.project.fast.ui.bottom_bar_screens.about

import androidx.lifecycle.ViewModel
import timber.log.Timber

class AboutScreenViewModel : ViewModel() {
    init {
        Timber.i(":: vm-init")
    }

    override fun onCleared() {
        Timber.i(":: vm-onCleared")
        super.onCleared()
    }
}