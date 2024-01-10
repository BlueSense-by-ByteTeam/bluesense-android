package com.byteteam.bluesense.core.data.remote.network.response.indonesian_location

import com.google.gson.annotations.SerializedName

data class GetProvince(

	@field:SerializedName("GetProvince")
	val getProvince: List<GetProvinceItem?>? = null
)

data class GetProvinceItem(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: DetailProvince? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class DetailProvince(

	@field:SerializedName("desa")
	val desa: Int? = null,

	@field:SerializedName("kota")
	val kota: Int? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: Int? = null,

	@field:SerializedName("kabupaten")
	val kabupaten: Int? = null
)
