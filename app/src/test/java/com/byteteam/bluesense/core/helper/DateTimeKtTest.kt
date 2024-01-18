package com.byteteam.bluesense.core.helper

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.text.SimpleDateFormat
import java.util.Date


@RunWith(JUnit4::class)
class DateTimeKtTest{
    @Test
    fun `should return formatted date string when given valid date object`() {
        val formatter = SimpleDateFormat("MM-dd-yyyy hh:mm")
        val date =  formatter.parse("01-06-2024 12:00")
        val result = date.getFormatedDate()
        // Assert
        assertEquals("06/01/2024 12:00", result)
    }

    @Test
    fun `should return formatted datetime  ui`(){

        val strDate = "06/01/2023 12:00"

        val result = strDate.formatUIDate()
        val expected = "06 Jan 2023"
        assertEquals(expected, result)
    }

    @Test
    fun `should return formatted datetime in current year ui`(){
        val dateNow = Date()
        dateNow.date = 12
        dateNow.month = 0//month start from 0

        val strDate = dateNow.getFormatedDate()
        val result = strDate.formatUIDate()
        val expected = "12 Jan"
        assertEquals(expected, result)
    }

    @Test
    fun `should return formatted today datetime ui`(){
        val dateNow = Date()
        dateNow.hours = 12
        dateNow.minutes = 0
        val strDate = dateNow.getFormatedDate()
        val result = strDate.formatUIDate()
        val expected = "12:00"
        assertEquals(expected, result)
    }

    @Test
    fun `should return original string because it invalid format`(){
        val strDate = "invalid"
        val result = strDate.formatUIDate()
        val expected = "invalid"
        assertEquals(expected, result)
    }

    @Test
    fun `parse date from UTC format`(){
        val strDate = "2024-01-18T11:11:21.618Z"
        val result = strDate.parseDateStringWithTimeZoneGMT7()
        val expected = "18/01/2024 18:11"
        assertEquals(expected, result)
    }
}