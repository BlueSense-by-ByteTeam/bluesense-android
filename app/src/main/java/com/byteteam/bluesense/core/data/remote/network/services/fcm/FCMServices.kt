package com.byteteam.bluesense.core.data.remote.network.services.fcm

import android.service.autofill.UserData
import com.byteteam.bluesense.core.data.remote.network.response.auth.UserDataResponse
import com.byteteam.bluesense.core.data.remote.network.response.fcm.FCMDetailResponse
import com.byteteam.bluesense.core.domain.model.SignUpPost
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface FCMServices{
    @GET("info/{token}")
    suspend fun getDetails(
        @Header("Authorization") authToken: String,
        @Path("token") token: String,
        @Query("details") details: Boolean = true,
    ): FCMDetailResponse
}