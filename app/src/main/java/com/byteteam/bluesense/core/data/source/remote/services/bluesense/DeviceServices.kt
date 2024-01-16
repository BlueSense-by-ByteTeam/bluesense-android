package com.byteteam.bluesense.core.data.source.remote.services.bluesense

import com.byteteam.bluesense.core.data.source.remote.response.MessageResponse
import com.byteteam.bluesense.core.data.source.remote.response.devices.GetDeviceLatestInfoResponse
import com.byteteam.bluesense.core.data.source.remote.response.devices.GetDevicesResponse
import com.byteteam.bluesense.core.domain.model.DevicePost
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface DeviceServices {
    @GET("api/devices")
    suspend fun getDevices(
        @Header("Authorization") authToken: String
    ): GetDevicesResponse

    @GET("api/devices/logs/{id}")
    suspend fun getDeviceLatestInfo(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ) : GetDeviceLatestInfoResponse

    @POST("api/devices")
    suspend fun postDevice(
        @Header("Authorization") authToken: String,
        @Body devicePost: DevicePost
    ): MessageResponse

    @DELETE("api/devices/{id}")
    suspend fun deleteDevice(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): MessageResponse
}