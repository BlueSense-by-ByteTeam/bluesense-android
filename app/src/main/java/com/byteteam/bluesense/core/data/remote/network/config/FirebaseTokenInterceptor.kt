package com.byteteam.bluesense.core.data.remote.network.config

import android.util.Log
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class FirebaseTokenInterceptor @Inject constructor(
    private val dataStorePreference: DataStorePreference
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)
        if(response.code == 200) Log.d("TAG", "intercept firebase token interceptor: okay")
        // Check if the response indicates that the access token is expired
        if (response.code == 401) {
            Log.d( this.javaClass.simpleName, "intercept: get new token")
            // Call the refresh token API to obtain a new access token
            val newAccessToken =
                runBlocking {
                    callRefreshTokenAPI()
                }
            response.close()
            // Create a new request with the updated access token
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
            // Retry the request with the new access token
            return chain.proceed(newRequest)
        }
        return response
    }

    private suspend fun callRefreshTokenAPI(): String {
        try {
            val user = Firebase.auth.currentUser
            val rawToken = user!!.getIdToken(true).await()
            val token = rawToken.token!!
            dataStorePreference.setAuthToken(token)//save new token to datastore preference

            return token
        } catch (e: Exception) {
            throw e
        }
    }
}