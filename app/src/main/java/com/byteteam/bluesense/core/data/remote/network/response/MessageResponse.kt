package com.byteteam.bluesense.core.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(

	@field:SerializedName("message")
	val message: String? = null
)
