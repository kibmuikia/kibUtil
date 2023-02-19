package kib.project.fast.ui.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.core.settings.general.GeneralSettingsManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashScreenViewModel(
    private val generalSettingsManager: GeneralSettingsManager,
) : ViewModel() {

    private val _isUserFirstVisit: MutableState<Boolean> = mutableStateOf(true)
    val isUserFirstVisit: State<Boolean> get() = _isUserFirstVisit

    init {
        Timber.v(":: vm-init")
        viewModelScope.launch {
            generalSettingsManager.isFirstVisit.collectLatest {
                _isUserFirstVisit.value = it
            }
        }
    }

    fun setIsUserFirstVisit(value: Boolean) {
        viewModelScope.launch {
            generalSettingsManager.setIsFirstVisit(value = value)
        }
    }

    override fun onCleared() {
        Timber.v(":: vm-cleared")
        super.onCleared()
    }

}