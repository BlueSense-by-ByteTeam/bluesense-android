package com.byteteam.bluesense.core.presentation.helper

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.byteteam.bluesense.BuildConfig
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GoogleSignInClient @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: SignInClient,
    private val dataStorePreference: DataStorePreference,
) {
    private val auth = Firebase.auth
    suspend fun signIn(): IntentSender? {
        val result = try {
            client.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            Log.d("TAG", "signIn: error")
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun getSignInResultFromIntent(intent: Intent): SignInResult {
        val credential = client.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            Log.d("TAG", "getSignInResultFromIntent: $user")
            val data = user?.let {
                dataStorePreference.setAuthToken(googleIdToken ?: "")
                UserData(
                    userId = it.uid,
                    userName = it.displayName ?: "-",
                    profilePicUrl = it.photoUrl.toString(),
                    email = it.email ?: "-",
                    credential = googleIdToken ?: "-"
                )
            }
            SignInResult(
                data = data,
                errorMessage = null
            )
        } catch (e: Exception) {
            Log.d("TAG", "getSignInResultFromIntent: ${e.message}")
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }


    suspend fun signOut() {
        try {
            client.signOut().await()
            auth.signOut()
            dataStorePreference.setAuthToken("")
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun getIdToken(): String? = auth.currentUser?.run {
        getIdToken(true).await().token
    }

    suspend fun getSignedUser(): UserData? = auth.currentUser?.run {
        val token = dataStorePreference.getAuthToken().firstOrNull()
        UserData(
            userId = uid,
            userName = displayName ?: "-",
            profilePicUrl = photoUrl.toString(),
            email = email ?: "-",
            credential = token
                ?: "-",// TODO: replace this credential with credential from userpreference
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.FIREBASE_SERVER_CLIENT_ID)
                .build()
        )
            .setAutoSelectEnabled(true)
            .build()
    }
}