package kib.project.data.api.models.requests

import com.google.gson.annotations.SerializedName

data class PostSmsRequest(
    @SerializedName("message") val message: String,
)