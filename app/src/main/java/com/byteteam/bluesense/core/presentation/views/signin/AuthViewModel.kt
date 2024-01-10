package com.byteteam.bluesense.core.presentation.views.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.model.UserData
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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


    init {
        getCurrentUser()
    }

    fun updateEmail(value: String){
        email.value = value
    }
    fun updatePassword(value: String){
        password.value = value
    }

    fun signInEmailPassword(callbackOnSuccess: () -> Unit = {}){
        viewModelScope.launch {
            repository.signInEmail(email.value, password.value).collect{
                val gson = Gson()
                val json = gson.toJson(it)
                Log.d("TAG", "onCreate: signed user $json")
                _currentUser.value = it?.data
                callbackOnSuccess()
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