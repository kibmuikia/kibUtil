package kib.project.data.api.interfaces

import kib.project.data.api.models.requests.SampleLoginUserRequest
import kib.project.data.api.models.responses.SampleUserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SampleApi {

    @POST("sample/auth/login")
    suspend fun sampleLoginUser(@Body sampleLoginUserRequest: SampleLoginUserRequest): SampleUserResponse

}