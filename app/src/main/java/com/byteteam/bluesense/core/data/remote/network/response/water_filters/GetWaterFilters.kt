package com.byteteam.bluesense.core.data.remote.network.response.water_filters

import com.google.gson.annotations.SerializedName

data class GetWaterFilters(

	@field:SerializedName("data")
	val data: List<WaterFilterItem?>? = null
)

data class WaterFilterItem(

	@field:SerializedName("tokopedia_url")
	val tokopediaUrl: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("shoppe_url")
	val shoppeUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
