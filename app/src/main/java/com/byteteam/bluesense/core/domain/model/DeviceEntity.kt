package com.byteteam.bluesense.core.domain.model

data class DeviceEntity(
    val id: String,
    val name: String,
    val waterSource: String,
    val address: String,
    val district: String,
    val city: String,
    val province: String,
    val mqttBaseUrl: String,
    val mqttTopic: String,
)
