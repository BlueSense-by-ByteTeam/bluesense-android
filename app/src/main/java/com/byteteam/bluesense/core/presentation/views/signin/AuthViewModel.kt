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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private var _currentUser: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val currentUser: StateFlow<UserData?> = _currentUser

    init {
        getCurrentUser()
    }

    private fun getCurrentUser(){
        viewModelScope.launch {
            repository.getCurrentUser().collect {
                val gson = Gson()
                val json = gson.toJson(it)
                Log.d("TAG", "onCreate: signed user $json")
                _currentUser.value = it
            }
        }
    }
}