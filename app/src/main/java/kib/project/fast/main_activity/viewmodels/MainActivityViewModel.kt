package kib.project.fast.main_activity.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.core.settings.general.GeneralSettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivityViewModel(
    private val generalSettingsManager: GeneralSettingsManager
) : ViewModel() {

    val isOnline = generalSettingsManager.isOnline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    private val _themeState: MutableStateFlow<Int> = MutableStateFlow(0)
    val themeState = _themeState.asStateFlow()

    init {
        viewModelScope.launch {
            generalSettingsManager.themeIndex.collectLatest { _themeState.value = it }
        }
    }

    override fun onCleared() {
        Timber.v(":: vm-cleared")
        super.onCleared()
    }

}