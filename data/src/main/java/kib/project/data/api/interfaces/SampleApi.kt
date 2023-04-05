package kib.project.data.api.interfaces

import kib.project.data.api.models.requests.PostSmsRequest
import kib.project.data.api.models.requests.SampleLoginUserRequest
import kib.project.data.api.models.responses.GenreResponse
import kib.project.data.api.models.responses.PostSmsResponse
import kib.project.data.api.models.responses.SampleUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SampleApi {

    @POST("sample/auth/login")
    suspend fun sampleLoginUser(
        @Body sampleLoginUserRequest: SampleLoginUserRequest,
    ): SampleUserResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenresList(
        @Query("language") language: String = "en-US",
    ): GenreResponse

    @POST("transaction/create")
    suspend fun postSms(
        @Body postSmsRequest: PostSmsRequest
    ): PostSmsResponse

}