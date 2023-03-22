package kib.project.data.database

import User
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kib.project.data.database.dao.AppSmsMessageDao
import kib.project.data.database.dao.UserDao
import kib.project.data.database.entities.textMessage.AppSmsMessage

@androidx.room.Database(
    entities = [
        User::class,
        AppSmsMessage::class,
    ],
    version = 2,
    exportSchema = false
)

// @TypeConverters()

abstract class AppDatabase : RoomDatabase() {

    // lisf of Dao
    abstract fun userDao(): UserDao
    abstract fun appSmsMessageDao(): AppSmsMessageDao

    companion object {
        private const val NAME_OF_DATABASE = "sample-app-database"

        fun create(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            NAME_OF_DATABASE
        ).build()
    }
}