package com.byteteam.bluesense.core.data.source.local.room
import com.byteteam.bluesense.core.data.source.local.room.dao.NotificationDao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity

@Database(
    entities = [NotificationEntity::class,],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java,
                        "bluesense_db"
                    ).build()
                }
                return INSTANCE as MainDatabase
            }
        }
    }
}