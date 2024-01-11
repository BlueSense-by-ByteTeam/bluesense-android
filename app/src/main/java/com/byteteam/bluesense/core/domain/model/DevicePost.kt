package com.byteteam.bluesense.core.domain.model

import com.google.gson.annotations.SerializedName

data class DevicePost(
    @SerializedName("device_id") val deviceId: String,
    val name: String,
    val province: String,
    val city: String,
    val district: String,
    val address: String,
    @SerializedName("water_source") val waterSource: String
)