package com.byteteam.bluesense.core.data.repositories

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.remote.network.response.MessageResponse
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.DeviceServices
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DevicePost
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
import com.byteteam.bluesense.core.helper.toDeviceEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference,
    private val deviceServices: DeviceServices
) : DeviceRepository{
    override suspend fun getDevices(): Flow<List<DeviceEntity>> {
        try {
            val token = dataStorePreference.getAuthToken().firstOrNull()
            val result = deviceServices.getDevices(authToken = "Bearer $token")
            return flowOf(result.toDeviceEntities())
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getDetailDevice(id: String): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDevice(id: String): Flow<MessageResponse> {
        try {
            val token = dataStorePreference.getAuthToken().firstOrNull()
            val result = deviceServices.deleteDevice(authToken = "Bearer $token", id = id)
            return flowOf(result)
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun postDevice(data: DevicePost): Flow<MessageResponse> {
        try {
            val token = dataStorePreference.getAuthToken().firstOrNull()
            val result = deviceServices.postDevice(authToken = "Bearer $token", devicePost = data)
            return flowOf(result)
        }catch (e: Exception){
            throw e
        }
    }
}