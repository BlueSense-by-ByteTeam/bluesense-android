package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInEmail(email: String, password: String): Flow<SignInResult?>
    suspend fun signUpEmail(name: String, email: String, password: String): Flow<SignInResult?>
    suspend fun signInGoogle(): Flow<SignInResult?>
    suspend fun getCurrentUser(): Flow<UserData?>
    suspend fun signOut()
}