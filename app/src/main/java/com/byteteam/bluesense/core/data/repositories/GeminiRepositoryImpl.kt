package com.byteteam.bluesense.core.data.repositories

import android.util.Log
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.source.remote.services.gemini.GeminiServices
import com.byteteam.bluesense.core.domain.model.Content
import com.byteteam.bluesense.core.domain.model.GeminiChatPost
import com.byteteam.bluesense.core.domain.model.Part
import com.byteteam.bluesense.core.domain.repositories.GeminiRepository
import com.byteteam.bluesense.core.helper.toLogHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val geminiApiServices: GeminiServices
) : GeminiRepository {
    override suspend fun postChat(message: String): Flow<String> = flow {
        try {
            val postData = GeminiChatPost(
                contents = listOf(
                    Content(
                        parts = listOf(
                            Part(text = message)
                        )
                    )
                )
            )
            val response = geminiApiServices.postChat(chatPost = postData)
            val textResponse: String? = response.candidates?.first()?.content?.parts?.first()?.text
            if (textResponse != null) {
                emit(textResponse)
            } else {
                throw Exception("Response is null!")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}