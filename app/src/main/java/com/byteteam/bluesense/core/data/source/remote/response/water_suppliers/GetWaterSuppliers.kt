package com.byteteam.bluesense.core.data.source.remote.response.water_suppliers

import com.google.gson.annotations.SerializedName

data class GetWaterSuppliers(

	@field:SerializedName("data")
	val data: List<WaterSupplierItem?>? = null
)

data class WaterSupplierItem(

	@field:SerializedName("instagram_url")
	val instagramUrl: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
