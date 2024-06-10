package com.byteteam.bluesense.core.data.source.remote.response.gemini

import com.google.gson.annotations.SerializedName

data class GeminiResponse(

	@field:SerializedName("candidates")
	val candidates: List<CandidatesItem?>? = null,

	@field:SerializedName("usageMetadata")
	val usageMetadata: UsageMetadata? = null
)

data class UsageMetadata(

	@field:SerializedName("candidatesTokenCount")
	val candidatesTokenCount: Int? = null,

	@field:SerializedName("totalTokenCount")
	val totalTokenCount: Int? = null,

	@field:SerializedName("promptTokenCount")
	val promptTokenCount: Int? = null
)

data class CandidatesItem(

	@field:SerializedName("finishReason")
	val finishReason: String? = null,

	@field:SerializedName("index")
	val index: Int? = null,

	@field:SerializedName("safetyRatings")
	val safetyRatings: List<SafetyRatingsItem?>? = null,

	@field:SerializedName("content")
	val content: Content? = null
)

data class PartsItem(

	@field:SerializedName("text")
	val text: String? = null
)

data class SafetyRatingsItem(

	@field:SerializedName("probability")
	val probability: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class Content(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("parts")
	val parts: List<PartsItem?>? = null
)
