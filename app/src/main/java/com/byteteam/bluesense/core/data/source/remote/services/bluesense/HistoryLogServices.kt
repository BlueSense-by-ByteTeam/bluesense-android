package com.byteteam.bluesense.core.data.source.remote.services.bluesense

import com.byteteam.bluesense.core.data.source.remote.response.history.GetHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HistoryLogServices{
    @GET("api/devices/history/{device_id}/{time}")
    suspend fun getLogHistory(
        @Header("Authorization") authToken: String,
        @Path("device_id") deviceId: String,
        @Path("time") time: String = "today"
    ): GetHistoryResponse
}