package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.repositories.OnBoardingRepositoryImpl
import com.byteteam.bluesense.core.domain.repositories.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.PrivateKey
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {
    @Provides
    @Singleton
    fun provideOnBoardingRepository(dataStorePreference: DataStorePreference): OnBoardingRepository = OnBoardingRepositoryImpl(dataStorePreference)
}