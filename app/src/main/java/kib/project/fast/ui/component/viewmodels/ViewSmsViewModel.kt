package kib.project.fast.ui.component.viewmodels

import androidx.lifecycle.ViewModel
import kib.project.fast.ui.component.models.ViewSmsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class ViewSmsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ViewSmsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Timber.i(":: vm-init")
    }

    fun setShouldStartSmsRetrieval(value: Boolean) {
        _uiState.update {
            it.copy(shouldStartSMSRetrieval = value)
        }
    }

    fun setReceivedSms(value: String) {
        _uiState.update {
            it.copy(receivedSms = value)
        }
    }

    override fun onCleared() {
        Timber.i(":: vm-onCleared")
        super.onCleared()
    }
}