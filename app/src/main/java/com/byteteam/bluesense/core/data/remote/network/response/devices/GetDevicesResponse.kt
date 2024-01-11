package com.byteteam.bluesense.core.data.remote.network.response.devices

import com.google.gson.annotations.SerializedName

data class GetDevicesResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

data class DeviceDetail(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("device_id")
	val deviceId: String? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("user_device_id")
	val userDeviceId: String? = null,

	@field:SerializedName("water_source")
	val waterSource: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class DataItem(

	@field:SerializedName("device_id")
	val deviceId: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("device_detail")
	val deviceDetail: DeviceDetail? = null
)
