package kib.project.data.database.entities.textMessage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mpesa_sms")
data class MpesaSms(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val messageBody: String,
    val sender: String?,
    val timeStamp: Long,
)

fun Triple<String?, String, Long>.toMpesaSms(): MpesaSms = MpesaSms(
    id = 0,
    messageBody = this.second,
    sender = this.first,
    timeStamp = this.third
)