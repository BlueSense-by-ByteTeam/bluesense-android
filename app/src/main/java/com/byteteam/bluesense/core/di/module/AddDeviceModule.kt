package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.remote.network.services.location.IndonesianLocationAddressService
import com.byteteam.bluesense.core.data.repositories.IndoLocalAddressRepositoryImpl
import com.byteteam.bluesense.core.domain.repositories.IndoLocalAddressRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddDeviceModule {
    @Provides
    @Singleton
    fun provideIndoLocalRepository(
        localAddressServices: IndonesianLocationAddressService,
    ) : IndoLocalAddressRepository = IndoLocalAddressRepositoryImpl(localAddressServices)

//    @Provides
//    @Singleton
//    fun provideAuthUseCase(authRepository: AuthRepository) : IAuthUseCase = AuthUseCase(authRepository)
}