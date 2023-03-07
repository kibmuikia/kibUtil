package kib.project.data.database.dao

import User
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface UserDao: BaseDao<User> {

    @Query("SELECT * FROM User WHERE userId =:userId LIMIT 1")
    fun fetchUserByUserId(userId: String): Flow<User?>

}