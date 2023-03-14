package kib.project.fast.ui.bottom_bar_screens.home

import androidx.lifecycle.ViewModel
import kib.project.core.utils.NetworkCallResult
import kib.project.data.api.models.requests.SampleLoginUserRequest
import kib.project.data.api.models.responses.SampleUserResponse
import kib.project.data.database.repositories.SampleRepository
import timber.log.Timber

class HomeScreenViewModel(
    private val sampleRepository: SampleRepository,
) : ViewModel() {

    suspend fun sampleLoginUser() {
        val sampleLoginUserRequest = SampleLoginUserRequest(
            username = "Person Doe",
            password = "1234"
        )
        val result: NetworkCallResult<SampleUserResponse> =
            sampleRepository.sampleLoginUser(sampleLoginUserRequest = sampleLoginUserRequest)
        when (result) {
            is NetworkCallResult.Success -> {
                result.data
                Timber.i(":: sampleUserResponse[ ${result.data} ]")
            }

            is NetworkCallResult.Error -> {
                Timber.i(":: Error[ msg = ${result.message} ].")
            }
        }
    }

    init {
        Timber.i(":: vm-init")
    }

    override fun onCleared() {
        Timber.i(":: vm-cleared")
        super.onCleared()
    }

}