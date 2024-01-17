package com.byteteam.bluesense.core.di.module

import android.content.Context
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.repositories.AuthRepositoryImpl
import com.byteteam.bluesense.core.data.repositories.NotificationRepositoryImpl
import com.byteteam.bluesense.core.data.source.local.room.dao.NotificationDao
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.AuthServices
import com.byteteam.bluesense.core.data.source.remote.services.fcm.FCMServices
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.domain.repositories.NotificationRepository
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClientHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    @Singleton
    fun provideNotificationRepository(
        @ApplicationContext appContext: Context,
        dataStorePreference: DataStorePreference,
        notificationDao: NotificationDao,
    ) : NotificationRepository = NotificationRepositoryImpl(appContext, dataStorePreference, notificationDao)
}