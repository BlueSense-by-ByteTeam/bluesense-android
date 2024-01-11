package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.data.remote.network.response.MessageResponse
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DevicePost
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    suspend fun getDevices() : Flow<List<DeviceEntity>>
    suspend fun getDetailDevice(id: String) : Flow<String>
    suspend fun deleteDevice(id: String) : Flow<MessageResponse>
    suspend fun postDevice(data: DevicePost) : Flow<MessageResponse>
}