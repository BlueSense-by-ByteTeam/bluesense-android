package com.byteteam.bluesense.core.data.remote.network.config

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object APIClient {
    private var retrofit: Retrofit? = null

    private fun getRetrofitApi(baseUrl: String) : Retrofit?{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }
    fun clientLocalAddress(): Retrofit? = getRetrofitApi("https://alamat.thecloudalert.com/api")
    fun clientBluesense(): Retrofit? = getRetrofitApi("https://bluesenseapi-1-l2019661.deta.app")
}