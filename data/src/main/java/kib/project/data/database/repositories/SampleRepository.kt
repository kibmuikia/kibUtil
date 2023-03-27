package kib.project.data.database.repositories

import kib.project.core.utils.NetworkCallResult
import kib.project.core.utils.UNKNOWN_NETWORK_ERROR_ENCOUNTERED
import kib.project.data.api.interfaces.SampleApi
import kib.project.data.api.models.requests.PostSmsRequest
import kib.project.data.api.models.requests.SampleLoginUserRequest
import kib.project.data.api.models.responses.GenreResponse
import kib.project.data.api.models.responses.PostSmsResponse
import kib.project.data.api.models.responses.SampleUserResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

interface SampleRepository {
    suspend fun sampleLoginUser(
        sampleLoginUserRequest: SampleLoginUserRequest
    ): NetworkCallResult<SampleUserResponse>

    suspend fun getMovieGenresList(): NetworkCallResult<GenreResponse>

    suspend fun postSms(postSmsRequest: PostSmsRequest): NetworkCallResult<PostSmsResponse>
}

class SampleRepositoryImpl(
    private val sampleApi: SampleApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SampleRepository {
    override suspend fun sampleLoginUser(
        sampleLoginUserRequest: SampleLoginUserRequest
    ): NetworkCallResult<SampleUserResponse> = withContext(ioDispatcher) {
        try {
            NetworkCallResult.Success(data = sampleApi.sampleLoginUser(sampleLoginUserRequest = sampleLoginUserRequest))
        } catch (e: Exception) {
            Timber.i(":: ${e.localizedMessage}; \n$e\n")
            NetworkCallResult.Error(
                message = e.localizedMessage.ifBlank { UNKNOWN_NETWORK_ERROR_ENCOUNTERED },
                exception = e,
            )
        }
    }

    override suspend fun getMovieGenresList(): NetworkCallResult<GenreResponse> = withContext(ioDispatcher) {
        try {
            NetworkCallResult.Success(data = sampleApi.getMovieGenresList())
        } catch (exception: Exception) {
            Timber.i(":: ${exception.localizedMessage}; \n$exception\n")
            Timber.e(exception)
            NetworkCallResult.Error(
                message = exception.localizedMessage.ifBlank { UNKNOWN_NETWORK_ERROR_ENCOUNTERED },
                exception = exception,
            )
        }
    }

    override suspend fun postSms(postSmsRequest: PostSmsRequest): NetworkCallResult<PostSmsResponse> = withContext(ioDispatcher) {
        try {
            NetworkCallResult.Success(data = sampleApi.postSms(postSmsRequest = postSmsRequest))
        } catch (exception: Exception) {
            Timber.i(":: ${exception.localizedMessage}; \n$exception\n")
            Timber.e(exception)
            NetworkCallResult.Error(
                message = exception.localizedMessage.ifBlank { UNKNOWN_NETWORK_ERROR_ENCOUNTERED },
                exception = exception,
            )
        }
    }
}