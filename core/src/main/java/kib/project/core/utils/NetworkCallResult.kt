package kib.project.core.utils

sealed class NetworkCallResult<out R> {
    data class Success<out T>(val data: T) : NetworkCallResult<T>()
    data class Error(
        val message: String = UNKNOWN_NETWORK_ERROR_ENCOUNTERED,
        val code: Int? = null,
        val exception: Exception? = null
    ) : NetworkCallResult<Nothing>()
}
