package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.remote.network.services.IndonesianLocationAddressService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//    val certificate =  CertificatePinner.Builder()
//        .add(
//            "alamat.thecloudalert.com",
//            "sha256/CHBwWWEtQ0h2y44qVzGNXVTQ++Z6IX9TYGJ4qid0NqA="
//        )
//        .add(
//            "alamat.thecloudalert.com",
//            "sha256/B0rUPCEbZgF/ekt7I0dVyypdUtUN2mIoilwTDcyqhA0="
//        )
//        .build()
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides
    @Singleton
    fun provideRetrofitLocalAddress(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://davidaprilio.github.io/data-lokasi-indonesia/json/")// TODO: change api base url
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): IndonesianLocationAddressService = retrofit.create(
        IndonesianLocationAddressService::class.java
    )
}