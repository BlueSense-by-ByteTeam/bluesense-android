package com.byteteam.bluesense.core.data.source.remote.services.bluesense

import android.service.autofill.UserData
import com.byteteam.bluesense.core.data.source.remote.response.auth.UserDataResponse
import com.byteteam.bluesense.core.domain.model.SignUpPost
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthServices{
    @GET("api/users")
    suspend fun getUserData(
        @Header("Authorization") authToken: String
    ): UserDataResponse
    @POST("api/users/register")
    suspend fun registerUser(
        @Header("Authorization") authToken: String,
        @Body signUpData: SignUpPost): UserDataResponse
}