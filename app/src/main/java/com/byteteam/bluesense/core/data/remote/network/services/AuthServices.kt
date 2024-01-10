package com.byteteam.bluesense.core.data.remote.network.services

import android.service.autofill.UserData
import com.byteteam.bluesense.core.data.remote.network.response.local_address_api.GetDistricts
import com.byteteam.bluesense.core.domain.model.SignUpPost
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthServices{
    @GET("/api/users")
    suspend fun getUserData(
        @Header("Authorization") authToken: String
    ): UserData
    @POST("/api/users/register")
    fun registerUser(
        @Header("Authorization") authToken: String,
        @Body signUpData: SignUpPost): UserData
}