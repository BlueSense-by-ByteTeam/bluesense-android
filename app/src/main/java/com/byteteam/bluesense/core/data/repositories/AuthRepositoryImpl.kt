package com.byteteam.bluesense.core.data.repositories

import android.util.Log
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.AuthServices
import com.byteteam.bluesense.core.data.remote.network.services.fcm.FCMServices
import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.SignUpPost
import com.byteteam.bluesense.core.domain.model.UserData
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.helper.getTopics
import com.byteteam.bluesense.core.presentation.helper.GoogleSignInClientHelper
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference,
    private val googleSignInClient: GoogleSignInClientHelper,
    private val authServices: AuthServices,
    private val fcmServices: FCMServices
) : AuthRepository {

    private suspend fun unsubsAllFCMTopics(){
        val fcmToken = FirebaseMessaging.getInstance().token.await()
        val response = fcmServices.getDetails(authToken = com.byteteam.bluesense.BuildConfig.FCM_ACCESS_KEY, token = fcmToken)
        val topics = response.getTopics()
        topics.map {
            Log.d("Subscribed topic", "signOut: $it")
            FirebaseMessaging.getInstance().unsubscribeFromTopic(it)
        }
    }
    override suspend fun signInEmail(email: String, password: String): Flow<SignInResult?> {
        try {
            unsubsAllFCMTopics()

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
        try {
            val idToken = result.user!!.getIdToken(true).await()
            //todo add register user endpoint
            val signUpPost = SignUpPost(
                name = name,
                email = email
            )
            val response = authServices.registerUser(
                authToken = "Bearer ${idToken.token}",
                signUpData = signUpPost
            )

            val userName = response.data?.name ?: "-"

            val data = UserData(
                userId = result.user?.uid ?: "",
                userName = result.user?.displayName ?: userName ,
                email = result.user?.email ?: "-",
                profilePicUrl = result.user?.photoUrl.toString() ,
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

    override suspend fun signupGoogle(name: String, email: String): Flow<SignInResult?> {
        try {
            val user = Firebase.auth.currentUser
            val idToken = user!!.getIdToken(true).await()
            dataStorePreference.setAuthToken(idToken.token ?: "")


            Log.d("signup google", "getCurrentUser: ${idToken.token}")

            val signUpPost = SignUpPost(
                name = name,
                email = email
            )
            authServices.registerUser(
                authToken = "Bearer ${idToken.token}",
                signUpData = signUpPost
            )

            val data = UserData(
                userId = user?.uid ?: "",
                userName = user?.displayName ?: name,
                email = user?.email ?: "-",
                profilePicUrl = user?.photoUrl.toString() ,
                credential = idToken.token ?: ""
            )
            return flowOf(SignInResult(data = data, errorMessage = null))
        }catch (e: Exception){
            return flowOf(SignInResult(data = null, errorMessage = e.message))
        }
    }

    override suspend fun getCurrentUser(): Flow<UserData?> {
        try {

            val user = Firebase.auth.currentUser
    //        val user = Firebase.auth.currentUser
            val idToken = user!!.getIdToken(true).await()
            val token = dataStorePreference.getAuthToken().firstOrNull()

            Log.d("TAG", "getCurrentUser: ${idToken.token}")
            // TODO: get data from api bluesense
            val data = user?.let {
                UserData(
                    userId = it.uid ?: "-",
                    userName = it.displayName ?: "-",
                    profilePicUrl = it.photoUrl.toString(),
                    email = it.email ?: "-",
                    credential = token ?: ""
                )
            }
            return flowOf(data)
        }catch (e: Exception){
            e.printStackTrace()
            return flowOf(null)
        }
    }

    override suspend fun signOut() {
        try {
           unsubsAllFCMTopics()
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            googleSignInClient.signOut()
        }
    }
    override suspend fun forgotPassword(email: String): Flow<String> {
        try {
            Firebase.auth.sendPasswordResetEmail(email).await()
            return flowOf("Success send reset password link to given email!")
        }catch (e: Exception){
            throw e
        }
    }

    private fun generateAvatar(name: String): String = "https://ui-avatars.com/api/?size=128&background=0D8ABC&color=fff&name=$name"
}