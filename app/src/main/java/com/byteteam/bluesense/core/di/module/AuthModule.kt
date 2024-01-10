package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.remote.network.services.LocalAddressServices
import com.byteteam.bluesense.core.data.repositories.AuthRepositoryImpl
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClient
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
        googleSignInClient: GoogleSignInClient,
        localAddressServices: LocalAddressServices,
    ) : AuthRepository = AuthRepositoryImpl(dataStorePreference, googleSignInClient, localAddressServices)

//    @Provides
//    @Singleton
//    fun provideAuthUseCase(authRepository: AuthRepository) : IAuthUseCase = AuthUseCase(authRepository)
}