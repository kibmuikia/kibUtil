package kib.project.data.database

import User
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kib.project.data.database.dao.UserDao

@androidx.room.Database(
    entities = [
        User::class,
    ],
    version = 1,
    exportSchema = false
)

// @TypeConverters()

abstract class AppDatabase : RoomDatabase() {

    // lisf of Dao
    abstract fun userDao(): UserDao

    companion object {
        private const val NAME_OF_DATABASE = "sample-app-database"

        fun create(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            NAME_OF_DATABASE
        ).build()
    }
}