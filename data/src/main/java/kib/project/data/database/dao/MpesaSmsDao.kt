package kib.project.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kib.project.data.database.entities.textMessage.MpesaSms
import kotlinx.coroutines.flow.Flow

@Dao
interface MpesaSmsDao: BaseDao<MpesaSms> {

    @Query("SELECT * FROM mpesa_sms ORDER BY timeStamp DESC")
    fun getAllMpesaSms(): Flow<List<MpesaSms>>

    @Query("SELECT * FROM mpesa_sms WHERE id = :id")
    fun getMpesaSmsById(id: Long): Flow<MpesaSms?>

    @Query("SELECT * FROM mpesa_sms WHERE sender = :sender")
    fun getMpesaSmsBySender(sender: String): Flow<List<MpesaSms>>

    @Query("SELECT * FROM mpesa_sms WHERE timeStamp > :timestamp")
    fun getMpesaSmsAfterTimestamp(timestamp: Long): Flow<List<MpesaSms>>
}