package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.core.data.remote.network.services.LocalAddressServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val certificate =  CertificatePinner.Builder()
        .add(
            "alamat.thecloudalert.com",
            "sha256/CHBwWWEtQ0h2y44qVzGNXVTQ++Z6IX9TYGJ4qid0NqA="
        )
        .add(
            "alamat.thecloudalert.com",
            "sha256/B0rUPCEbZgF/ekt7I0dVyypdUtUN2mIoilwTDcyqhA0="
        )
        .build()
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .certificatePinner(certificate)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitLocalAddress(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://alamat.thecloudalert.com/api/")// TODO: change api base url
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): LocalAddressServices = retrofit.create(
        LocalAddressServices::class.java
    )
}