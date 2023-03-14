package kib.project.data.api.models.responses

import com.google.gson.annotations.SerializedName

data class SampleUserResponse(
    @SerializedName("user") val user: UserResponse
)

data class UserResponse(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("system_id") val systemId: String,
)

data class GenreResponse(
    @SerializedName("genres") val genres: List<Genre>,
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
