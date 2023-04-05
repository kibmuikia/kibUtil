package kib.project.fast.ui.component.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.data.database.entities.textMessage.MpesaSms
import kib.project.data.database.entities.textMessage.toMpesaSms
import kib.project.data.database.repositories.MpesaSmsRepository
import kib.project.fast.ui.component.models.MpesaTextMessageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MpesaTextMessageViewModel(
    private val mpesaSmsRepository: MpesaSmsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MpesaTextMessageUiState())
    val uiState = _uiState.asStateFlow()

    fun setSmsPermissionState(state: Boolean) = viewModelScope.launch {
        _uiState.update {
            it.copy(isSmsPermissionGranted = state)
        }
    }

    fun setMpesaSmsTriple(triple: Triple<String?, String, Long>) = viewModelScope.launch {
        _uiState.update {
            it.copy(mpesaSmsTriple = triple)
        }
        triple.toMpesaSms().let { mpesaSms: MpesaSms ->
            mpesaSmsRepository.saveMpesaSms(mpesaSms)
        }
    }

    private fun fetchPreviousMpesaMessages() = viewModelScope.launch {
        mpesaSmsRepository.getAllMpesaSms().collectLatest { messages: List<MpesaSms> ->
            if (messages.isNotEmpty()) {
                _uiState.update {
                    it.copy(previousMpesaMessages = messages)
                }
            }
        }
    }

    init {
        fetchPreviousMpesaMessages()
    }
}