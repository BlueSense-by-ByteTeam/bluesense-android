package com.byteteam.bluesense.core.helper

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

fun Date.getFormatedDate(): String{
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm")
    val currentDate = sdf.format(this)
    return currentDate
}
fun String.formatUIDate(): String{
    val today = SimpleDateFormat("dd/MM/yyyy")
    val year = SimpleDateFormat("yyyy")
    val currentDate = today.format(Date())
    val currentYear = year.format(Date())
    try {
        if(this.contains(currentDate)){
            val formatTime = SimpleDateFormat("hh:mm")
            val date = SimpleDateFormat("dd/MM/yyyy hh:mm").parse(this)
            return formatTime.format(date)
        }else if(!this.contains(currentDate) && this.contains(currentYear)){
            val formatDate = SimpleDateFormat("dd MMM")
            val date = SimpleDateFormat("dd/MM/yyyy hh:mm").parse(this)
            return formatDate.format(date)
        }else{
            val formatDate = SimpleDateFormat("dd MMM YYYY")
            val date = SimpleDateFormat("dd/MM/yyyy hh:mm").parse(this)
            return formatDate.format(date)
        }
    }catch (e: Exception){
        e.printStackTrace()
        return this
    }
}