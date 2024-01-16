package com.byteteam.bluesense.core.data.repositories

import android.util.Log
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.DeviceServices
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.HistoryLogServices
import com.byteteam.bluesense.core.domain.model.LogHistoryEntity
import com.byteteam.bluesense.core.domain.repositories.HistoryLogRepository
import com.byteteam.bluesense.core.helper.toLogHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HistoryLogRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference,
    private val deviceServices: DeviceServices,
    private val historyLogServices: HistoryLogServices,
) : HistoryLogRepository {
    override suspend fun getTodayHistory(): Flow<Resource<LogHistoryEntity>> = flow {
        emit(Resource.Loading())
        try {
            val token = dataStorePreference.getAuthToken().first()
            val device = deviceServices.getDevices(authToken = "Bearer $token").data?.get(0)
                ?: throw Exception("Belum ada perangkat pada akun anda!")

            Log.d("History", "getTodayHistory: ${device.deviceId}")

            val response = historyLogServices.getLogHistory(authToken = "Bearer $token", deviceId = device.deviceId!!).toLogHistoryEntity()
            emit(Resource.Success(response))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "Error saat mengambil data history"))
        }
    }

    override suspend fun getWeekHistory(): Flow<Resource<LogHistoryEntity>> = flow {
        emit(Resource.Loading())
        try {
            val token = dataStorePreference.getAuthToken().first()
            val device = deviceServices.getDevices(authToken = "Bearer $token").data?.get(0)
                ?: throw Exception("Belum ada perangkat pada akun anda!")

            val response = historyLogServices.getLogHistory(authToken = "Bearer $token", deviceId = device.deviceId!!, time = "week").toLogHistoryEntity()
            emit(Resource.Success(response))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "Error saat mengambil data history"))
        }
    }

    override suspend fun getMonthHistory(): Flow<Resource<LogHistoryEntity>> = flow {
        emit(Resource.Loading())
        try {
            val token = dataStorePreference.getAuthToken().first()
            val device = deviceServices.getDevices(authToken = "Bearer $token").data?.get(0)
                ?: throw Exception("Belum ada perangkat pada akun anda!")

            val response = historyLogServices.getLogHistory(authToken = "Bearer $token", deviceId = device.deviceId!!, time = "month").toLogHistoryEntity()
            emit(Resource.Success(response))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "Error saat mengambil data history"))
        }
    }

    override suspend fun getYearHistory(): Flow<Resource<LogHistoryEntity>> = flow {
        emit(Resource.Loading())
        try {
            val token = dataStorePreference.getAuthToken().first()
            val device = deviceServices.getDevices(authToken = "Bearer $token").data?.get(0)
                ?: throw Exception("Belum ada perangkat pada akun anda!")

            val response = historyLogServices.getLogHistory(authToken = "Bearer $token", deviceId = device.deviceId!!, time = "year").toLogHistoryEntity()
            emit(Resource.Success(response))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "Error saat mengambil data history"))
        }
    }
}