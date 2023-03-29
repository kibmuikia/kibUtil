package kib.project.fast.ui.component.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.fast.ui.component.models.MpesaTextMessageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MpesaTextMessageViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MpesaTextMessageUiState())
    val uiState = _uiState.asStateFlow()

    fun setSmsPermissionState(state: Boolean) = viewModelScope.launch {
        _uiState.update {
            it.copy(isSmsPermissionGranted = state)
        }
    }

    fun setMpesaMessage(message: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(mpesaMessage = message)
        }
    }
}