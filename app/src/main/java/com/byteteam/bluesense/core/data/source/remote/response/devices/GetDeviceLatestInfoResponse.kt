package com.byteteam.bluesense.core.data.source.remote.response.devices

import com.google.gson.annotations.SerializedName

data class GetDeviceLatestInfoResponse(

	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(

	@field:SerializedName("drinkable")
	val drinkable: String? = null,

	@field:SerializedName("log")
	val log: Log? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("quality")
	val quality: String? = null
)

data class Log(

	@field:SerializedName("tds")
	val tds: Int? = null,

	@field:SerializedName("ph")
	val ph: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null
)
