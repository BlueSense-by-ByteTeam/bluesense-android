package com.byteteam.bluesense.core.presentation.helper

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun Long.formatPrice(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    format.currency = Currency.getInstance("IDR")
    return format.format(this).replace("IDR", "")
}
