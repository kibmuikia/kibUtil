package kib.project.data.database.repositories

import kib.project.data.api.interfaces.SampleApi
import kib.project.data.api.models.requests.SampleLoginUserRequest
import kib.project.data.api.models.responses.SampleUserResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SampleRepository {
    suspend fun sampleLoginUser(
        sampleLoginUserRequest: SampleLoginUserRequest
    ): SampleUserResponse
}

class SampleRepositoryImpl(
    private val sampleApi: SampleApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): SampleRepository {
    //
    override suspend fun sampleLoginUser(
        sampleLoginUserRequest: SampleLoginUserRequest
    ): SampleUserResponse = withContext(ioDispatcher) {
        sampleApi.sampleLoginUser(sampleLoginUserRequest = sampleLoginUserRequest)
    }
}