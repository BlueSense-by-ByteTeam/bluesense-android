package com.byteteam.bluesense.core.data.remote.network.response.indonesian_location

import com.google.gson.annotations.SerializedName

data class GetCities(

	@field:SerializedName("GetCities")
	val getCities: List<GetCitiesItem?>? = null
)

data class DetailCity(

	@field:SerializedName("desa")
	val desa: Int? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: Int? = null
)

data class GetCitiesItem(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: DetailCity? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
