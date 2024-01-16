package com.byteteam.bluesense.core.domain.model

data class LogHistoryEntity(
    val averageTds: Double,
    val averageQuality: String,
    val averagePh: Double,
    val minTds: Double,
    val maxPh: Double,
    val maxTds: Double,
    val minPh: Double,
    val averageStatus: String,
    val averageDrinkable: String,
    val logs: List<LogEntity>,
)
