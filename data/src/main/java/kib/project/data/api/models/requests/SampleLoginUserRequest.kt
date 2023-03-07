package kib.project.data.api.models.requests

import com.google.gson.annotations.SerializedName

data class SampleLoginUserRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
