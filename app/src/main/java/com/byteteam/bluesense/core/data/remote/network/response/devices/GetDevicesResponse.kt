package com.byteteam.bluesense.core.data.remote.network.response.devices

import com.google.gson.annotations.SerializedName

data class GetDevicesResponse(

	@field:SerializedName("data")
	val data: List<DeviceItem?>? = null
)

data class Device(

	@field:SerializedName("device_id")
	val deviceId: String? = null,

	@field:SerializedName("mqtt_base_url")
	val mqttBaseUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("mqtt_topic")
	val mqttTopic: String? = null
)

data class DeviceItem(

	@field:SerializedName("device_id")
	val deviceId: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("device_detail")
	val deviceDetail: DeviceDetailData? = null,

	@field:SerializedName("device")
	val device: Device? = null
)

data class DeviceDetailData(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("device_id")
	val deviceId: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("district")
	val district: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("user_device_id")
	val userDeviceId: String? = null,

	@field:SerializedName("water_source")
	val waterSource: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
