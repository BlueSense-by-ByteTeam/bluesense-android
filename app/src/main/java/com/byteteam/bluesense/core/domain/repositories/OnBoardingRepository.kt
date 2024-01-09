package com.byteteam.bluesense.core.domain.repositories

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    fun checkIsFirstTimeOpen(): Flow<Boolean>
    suspend fun finishOnBoarding(): Unit
}