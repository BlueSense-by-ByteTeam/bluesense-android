package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.WaterFilterServices
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.WaterSupplierServices
import com.byteteam.bluesense.core.data.repositories.StoreRepositoryImpl
import com.byteteam.bluesense.core.domain.repositories.StoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object StoreModule {
    @Provides
    @Singleton
    fun provideStoreRepository(
        dataStorePreference: DataStorePreference,
        waterFilterServices: WaterFilterServices,
        waterSupplierServices: WaterSupplierServices
    ): StoreRepository = StoreRepositoryImpl(
        dataStorePreference = dataStorePreference,
        waterSupplierServices = waterSupplierServices,
        waterFilterServices = waterFilterServices
    )
}