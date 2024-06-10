package com.byteteam.bluesense.core.data.source.remote.services.gemini

import com.byteteam.bluesense.BuildConfig
import com.byteteam.bluesense.core.data.source.remote.response.gemini.GeminiResponse
import com.byteteam.bluesense.core.domain.model.GeminiChatPost
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiServices {
    @POST("v1/models/gemini-1.5-flash:generateContent")
    suspend fun postChat(
        @Body chatPost: GeminiChatPost,
        @Query("key") key: String = BuildConfig.GEMINI_API_KEY,
    ): GeminiResponse
}