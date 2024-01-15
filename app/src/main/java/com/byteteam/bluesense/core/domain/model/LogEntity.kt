package com.byteteam.bluesense.core.domain.model

import com.google.gson.annotations.SerializedName

data class LogEntity(
    val tds: Double,
    val ph: Double,
    val createdAt: String,
    val quality: Int,
    val status: Int
)
