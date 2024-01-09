package com.byteteam.bluesense.core.data.repositories

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.UserData
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference,
    private val googleSignInClient: GoogleSignInClient
) : AuthRepository {
    override suspend fun signInEmail(email: String, password: String): Flow<SignInResult?> {
        TODO("Not yet implemented")
    }

    override suspend fun signInGoogle(): Flow<SignInResult?> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): Flow<UserData?> {
        return flowOf(
            googleSignInClient.getSignedUser()
        )
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}