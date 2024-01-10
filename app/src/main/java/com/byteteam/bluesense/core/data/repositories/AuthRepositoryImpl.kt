package com.byteteam.bluesense.core.data.repositories

import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.UserData
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference,
    private val googleSignInClient: GoogleSignInClient,
) : AuthRepository {
    override suspend fun signInEmail(email: String, password: String): Flow<SignInResult?> {
        try {
            val result = Firebase.auth.signInWithEmailAndPassword(email, password).await()
            val idToken = result.user!!.getIdToken(true).await()
            val signInResult = SignInResult(
                data = UserData(
                    userId = result.user?.uid ?: "",
                    email = result.user?.email ?: "-",
                    userName = result.user?.displayName ?: "-",
                    profilePicUrl = result.user?.photoUrl.toString(),
                    credential = idToken.token ?: "",
                ),
                errorMessage = null
            )
            dataStorePreference.setAuthToken(idToken.token ?: "")
            return flowOf(signInResult)
        } catch (e: Exception) {
            e.printStackTrace()
            return flowOf(SignInResult(data = null, errorMessage = e.message.toString()))
        }
    }

    override suspend fun signUpEmail(
        name: String,
        email: String,
        password: String
    ): Flow<SignInResult?> {
        val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        val idToken = result.user!!.getIdToken(true).await()
        try {
            //todo add register user endpoint
            val data = UserData(
                userId = result.user?.uid ?: "",
                userName = result.user?.displayName ?: "-",
                email = result.user?.email ?: "-",
                profilePicUrl = result.user?.photoUrl.toString(),
                credential = idToken.token ?: ""
            )
            dataStorePreference.setAuthToken(idToken.token ?: "")
            return flowOf(SignInResult(data = data, errorMessage = null))
        } catch (e: Exception) {
            result?.user?.delete()
            e.printStackTrace()
            return flowOf(SignInResult(data = null, errorMessage = e.message.toString()))
        }
    }

    override suspend fun signInGoogle(): Flow<SignInResult?> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): Flow<UserData?> {
        return flowOf(
            googleSignInClient.getSignedUser()
        )
    }

    override suspend fun signOut() = googleSignInClient.signOut()
}