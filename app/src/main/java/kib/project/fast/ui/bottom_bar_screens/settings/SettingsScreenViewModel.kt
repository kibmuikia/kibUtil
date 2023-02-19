package kib.project.fast.ui.bottom_bar_screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.core.settings.general.GeneralSettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class SettingsScreenViewModel(
    private val generalSettingsManager: GeneralSettingsManager
) : ViewModel() {
    private val _themeState: MutableStateFlow<Int> = MutableStateFlow(0)
    val themeState = _themeState.asStateFlow()

    init {
        viewModelScope.launch {
            generalSettingsManager.themeIndex.collectLatest { themeIndex: Int ->
                _themeState.value = themeIndex
            }
        }
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