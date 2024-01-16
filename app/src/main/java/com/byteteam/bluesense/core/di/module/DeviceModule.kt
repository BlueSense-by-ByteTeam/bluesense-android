package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.repositories.DeviceRepositoryImpl
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.DeviceServices
import com.byteteam.bluesense.core.data.source.remote.services.fcm.FCMServices
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
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
            fcmServices: FCMServices,
        ) : DeviceRepository = DeviceRepositoryImpl(dataStorePreference, deviceServices, fcmServices)

}