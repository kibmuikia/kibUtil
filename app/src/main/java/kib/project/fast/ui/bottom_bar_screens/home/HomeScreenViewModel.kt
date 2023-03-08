package kib.project.fast.ui.bottom_bar_screens.home

import androidx.lifecycle.ViewModel
import kib.project.data.api.models.requests.SampleLoginUserRequest
import kib.project.data.api.models.responses.SampleUserResponse
import kib.project.data.database.repositories.UserRepository
import timber.log.Timber

class HomeScreenViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    suspend fun sampleLoginUser() {
        try {
            val sampleLoginUserRequest = SampleLoginUserRequest(
                username = "Person Doe",
                password = "1234"
            )
            val sampleUserResponse: SampleUserResponse =
                userRepository.sampleLoginUser(sampleLoginUserRequest = sampleLoginUserRequest)
            Timber.i(":: sampleUserResponse[ $sampleUserResponse ]")
        } catch (exception: Exception) {
            Timber.e(exception)
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