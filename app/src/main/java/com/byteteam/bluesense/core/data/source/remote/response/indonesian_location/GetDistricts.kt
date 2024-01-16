package com.byteteam.bluesense.core.data.source.remote.response.indonesian_location

import com.google.gson.annotations.SerializedName

data class GetDistricts(

	@field:SerializedName("GetDistricts")
	val getDistricts: List<GetDistrictsItem?>? = null
)

data class GetDistrictsItem(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("count")
	val count: Count? = null
)

data class Count(

	@field:SerializedName("desa")
	val desa: Int? = null
)
