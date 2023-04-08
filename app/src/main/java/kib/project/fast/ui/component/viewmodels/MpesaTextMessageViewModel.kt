package kib.project.fast.ui.component.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kib.project.core.utils.NetworkCallResult
import kib.project.data.api.models.requests.PostSmsRequest
import kib.project.data.database.entities.textMessage.MpesaSms
import kib.project.data.database.entities.textMessage.toMpesaSms
import kib.project.data.database.repositories.MpesaSmsRepository
import kib.project.data.database.repositories.SampleRepository
import kib.project.data.utils.createFakeMpesaTransaction
import kib.project.data.utils.toMpesaTransaction
import kib.project.fast.BuildConfig
import kib.project.fast.ui.component.models.MpesaTextMessageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class MpesaTextMessageViewModel(
    private val mpesaSmsRepository: MpesaSmsRepository,
    private val sampleRepository: SampleRepository,
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

    suspend fun postSms(postSmsRequest: PostSmsRequest) {
        viewModelScope.launch {
            when (val response = sampleRepository.postSms(postSmsRequest = postSmsRequest)) {
                is NetworkCallResult.Success -> {
                    response.data.let { postSmsResponse ->
                        Timber.i(":: Success[ data = $postSmsResponse ].")
                        postSmsResponse.let { response ->
                            if (response.isValid) {
                                postSmsResponse.toMpesaTransaction()?.let { mpesaTransaction ->
                                    mpesaSmsRepository.saveMpesaTransaction(mpesaTransaction = mpesaTransaction)
                                }
                            }
                        }
                    }
                }

                is NetworkCallResult.Error -> {
                    Timber.i(":: Error[ msg = ${response.message} ].")
                    if (BuildConfig.DEBUG) {
                        mpesaSmsRepository.saveMpesaTransaction(mpesaTransaction = createFakeMpesaTransaction())
                    }
                }
            }
        }
    }
}