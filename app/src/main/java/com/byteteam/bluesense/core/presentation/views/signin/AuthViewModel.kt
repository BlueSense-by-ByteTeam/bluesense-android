package com.byteteam.bluesense.core.presentation.views.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.InputData
import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.UserData
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.presentation.tokens.EMAIL_REGEX
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private var _currentUser: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val currentUser: StateFlow<UserData?> = _currentUser

    val email: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val password: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val buttonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val googleSigninEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)


    private val eventChannel = Channel<SingleEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        getCurrentUser()
    }

    fun resetState() {
        email.value = InputData("")
        password.value = InputData("")
        buttonEnabled.value = false
        googleSigninEnabled.value = true
    }

    fun updateEmail(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Email tidak boleh kosong"
                !value.matches(EMAIL_REGEX.toRegex()) -> "Format input email salah!"
                else -> null
            }
        )
        email.value = data
        updateEnableButton()
    }

    fun updatePassword(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Password tidak boleh kosong!"
                value.length < 6 -> "Password minimal harus 6 karakter!"
                else -> null
            }
        )
        password.value = data
        updateEnableButton()
    }

    private fun updateEnableButton() {
        buttonEnabled.value = email.value.data.isNotEmpty() && password.value.data.isNotEmpty()
    }

    fun signInEmailPassword(callbackOnSuccess: () -> Unit = {}) {
        buttonEnabled.value = false
        viewModelScope.launch {
            repository.signInEmail(email.value.data, password.value.data).collect {
                val gson = Gson()
                val json = gson.toJson(it)
                Log.d("TAG", "onCreate: signed user $json")
                _currentUser.value = it?.data
                buttonEnabled.value = true
                if (it?.errorMessage == null) callbackOnSuccess()
                it?.errorMessage?.let {
                    setErrorSignIn(it)
                }
            }
        }
    }

    suspend fun setErrorSignIn(error: String?) {
        error?.let {
            eventChannel.send(SingleEvent.MessageEvent(it))
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            repository.getCurrentUser().collect {
                val gson = Gson()
                val json = gson.toJson(it)
                Log.d("TAG", "onCreate: signed user $json")
                _currentUser.value = it
            }
        }
    }

    fun signOut(callbackOnSignOut: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.signOut()
                withContext(Dispatchers.Main) {
                    callbackOnSignOut()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun disableGoogleSigninButton() {
        googleSigninEnabled.value = false
    }

    fun enableGoogleSigninButton() {
        googleSigninEnabled.value = true
    }
}