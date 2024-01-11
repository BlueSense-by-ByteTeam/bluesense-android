package com.byteteam.bluesense.core.presentation.views.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.UserData
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private var _currentUser: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val currentUser: StateFlow<UserData?> = _currentUser

    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")
    val buttonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val eventChannel = Channel<SingleEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        getCurrentUser()
    }

    fun updateEmail(value: String){
        email.value = value
        updateEnableButton()
    }
    fun updatePassword(value: String){
        password.value = value
        updateEnableButton()
    }

    private fun updateEnableButton(){
        buttonEnabled.value = email.value.isNotEmpty() && password.value.isNotEmpty()
    }

    fun signInEmailPassword(callbackOnSuccess: () -> Unit = {}){
        buttonEnabled.value = false
        viewModelScope.launch {
            repository.signInEmail(email.value, password.value).collect{
                val gson = Gson()
                val json = gson.toJson(it)
                Log.d("TAG", "onCreate: signed user $json")
                _currentUser.value = it?.data
                buttonEnabled.value = true
                if(it?.errorMessage == null) callbackOnSuccess()
                it?.errorMessage?.let {
                    eventChannel.send(SingleEvent.MessageEvent("Signin Error: $it"))
                }
            }
        }
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            repository.getCurrentUser().collect {
                val gson = Gson()
                val json = gson.toJson(it)
                Log.d("TAG", "onCreate: signed user $json")
                _currentUser.value = it
            }
        }
    }

    fun signOut(callbackOnSignOut: () -> Unit){
        viewModelScope.launch {
            try {
                repository.signOut()
                callbackOnSignOut()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}