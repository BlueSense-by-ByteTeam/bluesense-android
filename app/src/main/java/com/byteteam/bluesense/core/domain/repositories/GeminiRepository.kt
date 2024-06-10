package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {
    suspend fun postChat(message: String): Flow<String>
}