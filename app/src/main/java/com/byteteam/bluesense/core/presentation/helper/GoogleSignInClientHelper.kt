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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GoogleSignInClientHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    val client: GoogleSignInClient,
    private val dataStorePreference: DataStorePreference,
) {
    private val auth = Firebase.auth

    suspend fun getSignInResult(task: Task<GoogleSignInAccount>): SignInResult{
        try {

            val account: GoogleSignInAccount =
                task.getResult(ApiException::class.java)!!
            val googleIdToken = account.idToken!!
            val googleCredentials =
                GoogleAuthProvider.getCredential(googleIdToken, null)
            val user = Firebase.auth.signInWithCredential(googleCredentials)
                .await().user

            val data = user?.let {
                dataStorePreference.setAuthToken(googleIdToken)
                UserData(
                    userId = it.uid,
                    userName = it.displayName ?: "-",
                    profilePicUrl = it.photoUrl.toString(),
                    email = it.email ?: "-",
                    credential = googleIdToken ?: "-"
                )
            }
            val signInResult = SignInResult(
                data = data,
                errorMessage = null
            )
            return signInResult
        }catch (e: Exception) {
            Log.d("TAG", "getSignInResultFromIntent: ${e.message}")
            e.printStackTrace()
            if (e is CancellationException) throw e
            return SignInResult(
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