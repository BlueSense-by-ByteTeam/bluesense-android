package com.byteteam.bluesense.core.data.remote.network.response.auth

import com.google.gson.annotations.SerializedName

data class UserDataResponse(

	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(

	@field:SerializedName("telp")
	val telp: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("firebase_id")
	val firebaseId: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
