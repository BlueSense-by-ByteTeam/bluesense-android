package com.byteteam.bluesense.core.data.source.remote.response.water_supplier_detail

import com.google.gson.annotations.SerializedName

data class WaterSupplierDetailResponse(

	@field:SerializedName("data")
	val data: Data? = null
)

data class LogsItem(

	@field:SerializedName("tds")
	val tds: Int? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("ph")
	val ph: Int? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Data(

	@field:SerializedName("detail_location")
	val detailLocation: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("id_user_water_supplier")
	val idUserWaterSupplier: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("latitude")
	val latitude: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: Any? = null,

	@field:SerializedName("logs")
	val logs: List<LogsItem?>? = null,

	@field:SerializedName("longitude")
	val longitude: Int? = null
)
