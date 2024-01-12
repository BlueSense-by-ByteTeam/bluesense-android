package com.byteteam.bluesense.core.domain.model

data class DeviceLatestInfoEntity(
    val id: String,
    val status: String,
    val quality: String,
    val ph: Double,
    val tds: Double,
    val timestamp: String,
)
