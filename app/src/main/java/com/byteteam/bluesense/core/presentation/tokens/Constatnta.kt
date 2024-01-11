package com.byteteam.bluesense.core.presentation.tokens

enum class SortDateLog{
    TODAY,
    WEEK,
    MONTH,
    YEAR
}

enum class SortData{
    QUALITY,
    STATUS,
    TDS,
    PH
}

const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
