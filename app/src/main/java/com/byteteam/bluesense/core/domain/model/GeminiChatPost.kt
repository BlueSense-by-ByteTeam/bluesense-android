package com.byteteam.bluesense.core.domain.model

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GeminiChatPost(
    val contents: List<Content>
)
