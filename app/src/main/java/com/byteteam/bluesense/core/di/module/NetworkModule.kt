package com.byteteam.bluesense.core.di.module

import com.byteteam.bluesense.BuildConfig
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.source.remote.config.FirebaseTokenInterceptor
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.AuthServices
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.DeviceServices
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.HistoryLogServices
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.WaterFilterServices
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.WaterSupplierServices
import com.byteteam.bluesense.core.data.source.remote.services.fcm.FCMServices
import com.byteteam.bluesense.core.data.source.remote.services.gemini.GeminiServices
import com.byteteam.bluesense.core.data.source.remote.services.location.IndonesianLocationAddressService
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
    val BLUESENSE_BASE_URL = BuildConfig.BLUESENSE_BASE_URL
    @Provides
    @Singleton
    fun provideFirebaseAuthTokenInterceptor(
        dataStorePreference: DataStorePreference,
    ): FirebaseTokenInterceptor = FirebaseTokenInterceptor(dataStorePreference)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        firebaseTokenInterceptor: FirebaseTokenInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(firebaseTokenInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideLocationApiService(retrofitBuilder: Retrofit.Builder): IndonesianLocationAddressService = retrofitBuilder.baseUrl("https://davidaprilio.github.io/data-lokasi-indonesia/json/").build().create(
        IndonesianLocationAddressService::class.java
    )
    //MAIN REST API (BLUESENSE SERVER)
    @Provides
    @Singleton
    fun provideAuthApiService(retrofitBuilder: Retrofit.Builder): AuthServices = retrofitBuilder.baseUrl(
        BLUESENSE_BASE_URL).build().create(
        AuthServices::class.java
    )

    @Provides
    @Singleton
    fun provideDeviceApiServices(retrofitBuilder: Retrofit.Builder): DeviceServices = retrofitBuilder.baseUrl(
        BLUESENSE_BASE_URL).build().create(
        DeviceServices::class.java
    )

    @Provides
    @Singleton
    fun provideWaterSuppliersApiServices(retrofitBuilder: Retrofit.Builder): WaterSupplierServices = retrofitBuilder.baseUrl(
        BLUESENSE_BASE_URL).build().create(
        WaterSupplierServices::class.java
    )
    @Provides
    @Singleton
    fun provideWaterFiltersApiServices(retrofitBuilder: Retrofit.Builder): WaterFilterServices = retrofitBuilder.baseUrl(
        BLUESENSE_BASE_URL).build().create(
        WaterFilterServices::class.java
    )
    @Provides
    @Singleton
    fun provideHistoryLogApiServices(retrofitBuilder: Retrofit.Builder): HistoryLogServices = retrofitBuilder.baseUrl(
        BLUESENSE_BASE_URL).build().create(
        HistoryLogServices::class.java
    )
    @Provides
    @Singleton
    fun provideFCMApiServices(retrofitBuilder: Retrofit.Builder): FCMServices = retrofitBuilder.baseUrl(
        "https://iid.googleapis.com/iid/").build().create(
        FCMServices::class.java
    )
    @Provides
    @Singleton
    fun provideGeminiApiServices(retrofitBuilder: Retrofit.Builder): GeminiServices = retrofitBuilder.baseUrl(
        "https://generativelanguage.googleapis.com/").build().create(
        GeminiServices::class.java
    )
}