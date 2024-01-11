package com.byteteam.bluesense.core.data.remote.network.services.bluesense

import android.service.autofill.UserData
import com.byteteam.bluesense.core.data.remote.network.response.MessageResponse
import com.byteteam.bluesense.core.data.remote.network.response.devices.GetDevicesResponse
import com.byteteam.bluesense.core.domain.model.SignUpPost
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

    @POST("api/devices")
    suspend fun postDevice(
        @Header("Authorization") authToken: String,
        @Body devicePoset: SignUpPost
    ): MessageResponse

    @DELETE("api/devices/{id}")
    suspend fun deleteDevice(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): MessageResponse
}