package kib.project.data.database.entities.textMessage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_messages")
data class AppSmsMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val address: String,
    val body: String,
    val date: Long,
    val person: Int?,
    val threadId: Long,
    val protocol: Int,
    val serviceCenter: String?,
    val status: Int,
    val type: Int
)

val testAppSmsMessage = AppSmsMessage(
    address = "+1234567890",
    body = "Hello, this is a test message!",
    date = System.currentTimeMillis(),
    person = null,
    threadId = 1L,
    protocol = 0,
    serviceCenter = "+1987654321",
    status = 1,
    type = 1
)

data class AppSmsMessageUiModel(
    val address: String?,
    val body: String?,
    val date: String?,
    val person: String? = null,
    val threadId: String?,
    val protocol: String?,
    val serviceCenter: String? = null,
    val status: String?,
    val type: String?
)