package kib.project.fast.ui.component.viewmodels

import androidx.lifecycle.ViewModel
import kib.project.fast.ui.component.models.AppProgressDialogUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class AppProgressDialogViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppProgressDialogUiState())
    val uiState = _uiState.asStateFlow()

    fun setUiStateShow(value: Boolean) {
        _uiState.update {
            it.copy(show = value)
        }
    }

    init {
        Timber.v(":: vm-init: triggered!")
    }

    override fun onCleared() {
        Timber.v(":: vm-onCleared: triggered!")
        super.onCleared()
    }
}