package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.AuthServices
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.DeviceServices
import com.byteteam.bluesense.core.data.repositories.AuthRepositoryImpl
import com.byteteam.bluesense.core.data.repositories.DeviceRepositoryImpl
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeviceModule {
        @Provides
        @Singleton
        fun provideDeviceRepository(
            dataStorePreference: DataStorePreference,
            deviceServices: DeviceServices,
        ) : DeviceRepository = DeviceRepositoryImpl(dataStorePreference, deviceServices)

}