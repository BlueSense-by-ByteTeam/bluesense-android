package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.AuthServices
import com.byteteam.bluesense.core.data.remote.network.services.fcm.FCMServices
import com.byteteam.bluesense.core.data.repositories.AuthRepositoryImpl
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClientHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        dataStorePreference: DataStorePreference,
        googleSignInClient: GoogleSignInClientHelper,
        authServices: AuthServices,
        fcmServices: FCMServices,
    ) : AuthRepository = AuthRepositoryImpl(dataStorePreference, googleSignInClient, authServices, fcmServices)
}