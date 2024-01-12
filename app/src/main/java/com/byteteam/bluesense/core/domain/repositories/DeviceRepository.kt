package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.data.remote.network.response.MessageResponse
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.core.domain.model.DevicePost
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    suspend fun getDevices() : Flow<List<DeviceEntity>>
    suspend fun getLatestDeviceDetail(id: String) : Flow<DeviceLatestInfoEntity?>
    suspend fun deleteDevice(id: String) : Flow<MessageResponse>
    suspend fun postDevice(data: DevicePost) : Flow<MessageResponse>
}