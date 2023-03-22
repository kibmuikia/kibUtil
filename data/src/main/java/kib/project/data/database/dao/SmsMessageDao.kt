package kib.project.data.database.dao

import androidx.room.Query
import kib.project.data.database.entities.textMessage.AppSmsMessage

interface AppSmsMessageDao : BaseDao<AppSmsMessage> {

    @Query("SELECT * FROM sms_messages")
    suspend fun getAllSmsMessages(): List<AppSmsMessage>

    @Query("SELECT * FROM sms_messages WHERE id = :id")
    suspend fun getSmsMessageById(id: Int): AppSmsMessage?

}