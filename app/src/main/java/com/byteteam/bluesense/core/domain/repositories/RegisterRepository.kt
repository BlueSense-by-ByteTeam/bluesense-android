package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.SignUpResult
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun signUp(email: String, password: String): Flow<SignUpResult?>
}