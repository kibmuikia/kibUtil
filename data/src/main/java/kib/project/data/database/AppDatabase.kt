package kib.project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kib.project.data.database.dao.AppSmsMessageDao
import kib.project.data.database.dao.MpesaSmsDao
import kib.project.data.database.dao.MpesaTransactionDao
import kib.project.data.database.dao.UserDao
import kib.project.data.database.entities.User
import kib.project.data.database.entities.textMessage.AppSmsMessage
import kib.project.data.database.entities.textMessage.MpesaSms
import kib.project.data.database.entities.textMessage.MpesaTransaction

// @TypeConverters()
@Database(
    entities = [
        User::class,
        AppSmsMessage::class,
        MpesaSms::class,
        MpesaTransaction::class,
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // lisf of Dao
    abstract fun userDao(): UserDao
    abstract fun appSmsMessageDao(): AppSmsMessageDao
    abstract fun mpesaSmsDao(): MpesaSmsDao
    abstract fun mpesaTransactionDao(): MpesaTransactionDao

    companion object {
        private const val NAME_OF_DATABASE = "sample-app-database"

        fun create(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            NAME_OF_DATABASE
        ).build()
    }
}