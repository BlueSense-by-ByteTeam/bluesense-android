package com.byteteam.bluesense.core.data.repositories

import android.util.Log
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.source.remote.response.MessageResponse
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.DeviceServices
import com.byteteam.bluesense.core.data.source.remote.services.fcm.FCMServices
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.core.domain.model.DevicePost
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
import com.byteteam.bluesense.core.helper.getTopics
import com.byteteam.bluesense.core.helper.toDeviceEntities
import com.byteteam.bluesense.core.helper.toDeviceLatestInfoEntity
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference,
    private val deviceServices: DeviceServices,
    private val fcmServices: FCMServices
) : DeviceRepository {
    override suspend fun getDevices(): Flow<List<DeviceEntity>> {
        try {
            val token = dataStorePreference.getAuthToken().firstOrNull()
            val result = deviceServices.getDevices(authToken = "Bearer $token")
            return flowOf(result.toDeviceEntities())
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getLatestDeviceDetail(id: String): Flow<DeviceLatestInfoEntity?> {
        try {
            val token = dataStorePreference.getAuthToken().firstOrNull()
            val result = deviceServices.getDeviceLatestInfo(authToken = "Bearer $token", id = id)
            return flowOf(result.toDeviceLatestInfoEntity(id))
        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun deleteDevice(id: String): Flow<MessageResponse> {
        try {
            unsubsAllFCMTopics()
            val token = dataStorePreference.getAuthToken().firstOrNull()
            val result = deviceServices.deleteDevice(authToken = "Bearer $token", id = id)
            return flowOf(result)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun postDevice(data: DevicePost): Flow<MessageResponse> {
        try {
            val token = dataStorePreference.getAuthToken().firstOrNull()
            val result = deviceServices.postDevice(authToken = "Bearer $token", devicePost = data)
            return flowOf(result)
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun unsubsAllFCMTopics(){
        val fcmToken = FirebaseMessaging.getInstance().token.await()
        val response = fcmServices.getDetails(authToken = com.byteteam.bluesense.BuildConfig.FCM_ACCESS_KEY, token = fcmToken)
        val topics = response.getTopics()
        topics.map {
            Log.d("Subscribed topic", "signOut: $it")
            FirebaseMessaging.getInstance().unsubscribeFromTopic(it)
        }
    }
}