package kib.project.data.database.repositories

import kib.project.data.database.dao.UserDao
import kib.project.data.database.entities.User
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

}

class UserRepositoryImpl(
    private val userDao: UserDao,
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

}