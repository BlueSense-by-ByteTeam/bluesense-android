package com.byteteam.bluesense.core.domain.model

data class UserData(
    val userId: String,
    val userName: String,
    val email: String,
    val profilePicUrl: String?,
    val credential: String,
)