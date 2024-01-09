package com.byteteam.bluesense.core.data.repositories

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.domain.repositories.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference
) : OnBoardingRepository{
    override fun checkIsFirstTimeOpen(): Flow<Boolean> =  dataStorePreference.getFirstOpenStatus()
    override suspend fun finishOnBoarding(){
        dataStorePreference.setFirstOpenStatus()
    }
}