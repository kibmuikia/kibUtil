package kib.project.data.database.usecases

import kib.project.data.database.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserUseCase(
    private val userRepository: UserRepository
) {
    // You can use KoinComponent to inject

    fun getFirstName(userId: String): Flow<String> = userRepository.getUserById(userId = userId)
        .map {
            it?.getFullName() ?: "User Not Found"
        }

}