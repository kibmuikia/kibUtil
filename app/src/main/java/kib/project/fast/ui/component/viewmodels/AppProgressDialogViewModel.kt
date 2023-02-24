package kib.project.fast.ui.component.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kib.project.fast.ui.component.models.AppProgressDialogUiState
import timber.log.Timber

class AppProgressDialogViewModel : ViewModel() {

    private val _uiState: MutableState<AppProgressDialogUiState> =
        mutableStateOf(AppProgressDialogUiState())
    val uiState: State<AppProgressDialogUiState> get() = _uiState

    init {
        Timber.v(":: vm-init: triggered!")
    }

    override fun onCleared() {
        Timber.v(":: vm-onCleared: triggered!")
        super.onCleared()
    }
}