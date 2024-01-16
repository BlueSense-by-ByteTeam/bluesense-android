package com.byteteam.bluesense.core.data.remote.network.response.fcm

import com.google.gson.annotations.SerializedName

data class FCMDetailResponse(

	@field:SerializedName("applicationVersion")
	val applicationVersion: String? = null,

	@field:SerializedName("gmiRegistrationId")
	val gmiRegistrationId: String? = null,

	@field:SerializedName("application")
	val application: String? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("authorizedEntity")
	val authorizedEntity: String? = null,

	@field:SerializedName("rel")
	val rel: Rel? = null,

	@field:SerializedName("appSigner")
	val appSigner: String? = null,

	@field:SerializedName("platform")
	val platform: String? = null
)

data class JsonMember4022D808AAA0(

	@field:SerializedName("addDate")
	val addDate: String? = null
)

data class Rel(

	@field:SerializedName("topics")
	val topics: Map<String, Any>? = null
)

data class Topics(

	@field:SerializedName("4022D808AAA0")
	val jsonMember4022D808AAA0: JsonMember4022D808AAA0? = null,

	@field:SerializedName("it_should_device_id")
	val itShouldDeviceId: ItShouldDeviceId? = null
)

data class ItShouldDeviceId(

	@field:SerializedName("addDate")
	val addDate: String? = null
)
