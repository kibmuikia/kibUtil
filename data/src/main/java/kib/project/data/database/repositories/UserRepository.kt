package kib.project.data.database.repositories

import User
import kib.project.data.api.interfaces.SampleApi
import kib.project.data.api.models.requests.SampleLoginUserRequest
import kib.project.data.api.models.responses.SampleUserResponse
import kib.project.data.database.dao.UserDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

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

    suspend fun sampleLoginUser(sampleLoginUserRequest: SampleLoginUserRequest): SampleUserResponse

}

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val sampleApi: SampleApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): UserRepository { // KoinComponent

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

    override fun getUserById(userId: String): Flow<User?> = userDao.fetchUserByUserId(userId = userId)
    override suspend fun sampleLoginUser(sampleLoginUserRequest: SampleLoginUserRequest): SampleUserResponse =
        sampleApi.sampleLoginUser(sampleLoginUserRequest = sampleLoginUserRequest)

}