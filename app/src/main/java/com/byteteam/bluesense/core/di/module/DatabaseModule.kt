package com.byteteam.bluesense.core.di.module

import android.content.Context
import androidx.room.Room
import com.byteteam.bluesense.core.data.source.local.room.MainDatabase
import com.byteteam.bluesense.core.data.source.local.room.dao.NotificationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): MainDatabase{
        return Room.databaseBuilder(
            appContext,
            MainDatabase::class.java,
            "bluesense_db"
        ).build()
    }
    @Provides
    fun provideAttendanceDao(appDatabase: MainDatabase): NotificationDao {
        return appDatabase.notificationDao()
    }
}