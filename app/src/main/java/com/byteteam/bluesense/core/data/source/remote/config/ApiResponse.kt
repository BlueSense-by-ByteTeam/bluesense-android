package com.byteteam.bluesense.core.data.source.remote.config

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : com.byteteam.bluesense.core.data.source.remote.config.ApiResponse<T>()
    data class Error(val errorMessage: String) : com.byteteam.bluesense.core.data.source.remote.config.ApiResponse<Nothing>()
    object Empty : com.byteteam.bluesense.core.data.source.remote.config.ApiResponse<Nothing>()
}