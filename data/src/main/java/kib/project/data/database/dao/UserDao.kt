package kib.project.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kib.project.data.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao: BaseDao<User> {

    @Query("SELECT * FROM User WHERE userId =:userId LIMIT 1")
    fun fetchUserByUserId(userId: String): Flow<User?>

}