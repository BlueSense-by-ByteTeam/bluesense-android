package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.repositories.AuthRepositoryImpl
import com.byteteam.bluesense.core.data.repositories.GeminiRepositoryImpl
import com.byteteam.bluesense.core.data.repositories.HistoryLogRepositoryImpl
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.DeviceServices
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.HistoryLogServices
import com.byteteam.bluesense.core.data.source.remote.services.gemini.GeminiServices
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.domain.repositories.GeminiRepository
import com.byteteam.bluesense.core.domain.repositories.HistoryLogRepository
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClientHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeminiModule {
    @Provides
    @Singleton
    fun provideGeminiRepository(
        geminiApiServices: GeminiServices,
    ) : GeminiRepository = GeminiRepositoryImpl(geminiApiServices)
}