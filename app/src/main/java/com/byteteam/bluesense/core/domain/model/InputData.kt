package com.byteteam.bluesense.core.domain.model

data class InputData(
    val data: String,
    val errorMessage: String? = null
)
fun InputData.copy(
    value: String = this.data,
    errorMessage: String? = this.errorMessage
) = InputData(value, errorMessage)