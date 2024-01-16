package com.byteteam.bluesense.core.data.source.remote.response.history

import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(

	@field:SerializedName("data")
	val data: Log? = null
)

data class Log(

	@field:SerializedName("average_tds")
	val averageTds: Double? = null,

	@field:SerializedName("average_quality")
	val averageQuality: String? = null,

	@field:SerializedName("average_ph")
	val averagePh: Double? = null,

	@field:SerializedName("min_tds")
	val minTds: Double? = null,

	@field:SerializedName("average_status")
	val averageStatus: String? = null,

	@field:SerializedName("max_ph")
	val maxPh: Double? = null,

	@field:SerializedName("max_tds")
	val maxTds: Double? = null,

	@field:SerializedName("logs")
	val logs: List<LogsItem?>? = null,

	@field:SerializedName("min_ph")
	val minPh: Double? = null,

	@field:SerializedName("average_drinkable")
	val averageDrinkable: String? = null
)

data class LogsItem(

	@field:SerializedName("tds")
	val tds: Double? = null,

	@field:SerializedName("ph")
	val ph: Double? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("quality")
	val quality: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
