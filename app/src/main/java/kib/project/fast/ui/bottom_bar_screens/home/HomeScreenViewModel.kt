package kib.project.fast.ui.bottom_bar_screens.home

import androidx.lifecycle.ViewModel
import timber.log.Timber

class HomeScreenViewModel : ViewModel() {

    init {
        Timber.i(":: vm-init")
    }

    override fun onCleared() {
        Timber.i(":: vm-cleared")
        super.onCleared()
    }

}