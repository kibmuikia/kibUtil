package kib.project.data.database.repositories

import User
import kib.project.core.utils.NetworkCallResult
import kib.project.data.api.interfaces.SampleApi
import kib.project.data.api.models.requests.SampleLoginUserRequest
import kib.project.data.api.models.responses.SampleUserResponse
import kib.project.data.database.dao.UserDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber

interface UserRepository {

    suspend fun saveUser(
        userId: String,
        userType: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        email: String,
        dateOfBirth: String?
    ): Long

    fun getUserById(userId: String): Flow<User?>

    suspend fun sampleLoginUser(sampleLoginUserRequest: SampleLoginUserRequest): NetworkCallResult<SampleUserResponse>

}

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val sampleApi: SampleApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository { // KoinComponent

    override suspend fun saveUser(
        userId: String,
        userType: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        email: String,
        dateOfBirth: String?
    ) = userDao.insert(
        User(
            id = 0,
            userId = userId,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            email = email,
            dateOfBirth = dateOfBirth
        )
    )

    override fun getUserById(userId: String): Flow<User?> =
        userDao.fetchUserByUserId(userId = userId)

    override suspend fun sampleLoginUser(
        sampleLoginUserRequest: SampleLoginUserRequest
    ): NetworkCallResult<SampleUserResponse> =
        withContext(ioDispatcher) {
            try {
                NetworkCallResult.Success(
                    data = sampleApi.sampleLoginUser(sampleLoginUserRequest = sampleLoginUserRequest)
                )
            } catch (exception: Exception) {
                Timber.e(exception)
                NetworkCallResult.Error(
                    message = "Error in sampleLoginUser",
                    exception = exception
                )
            }
        }

}