package com.byteteam.bluesense.core.domain.model

data class WaterFilterEntity(
    val id: String,
    val name: String,
    val price: Long,
    val rating: Double,
    val description: String,
    val tokopediaUrl: String,
    val shoppe_url: String,
    val imageUrl: String
)
