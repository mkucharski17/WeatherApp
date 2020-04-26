package com.kucharski.michal.weatheracc

import java.text.SimpleDateFormat
import java.util.*

fun getDateMonthYear(timeStamp: Int): String =
    SimpleDateFormat("dd MMM, YYYY  ").format(Date(timeStamp.toLong() * 1000))

fun getHourAndMinutes(timeStamp: Int): String =
    SimpleDateFormat("HH:mm").format(Date(timeStamp.toLong() * 1000))

fun getDayOfWeek(timeStamp: Int): String =
    SimpleDateFormat("EEE").format(Date(timeStamp.toLong() * 1000))

fun getHourMinutesMonthDay(timeZone: Int): String {
    val formatter = SimpleDateFormat("HH:mm MMM d", Locale.US)
    val formattedDate = formatter.format(Date(Date().time + timeZone * 1000))
    return formattedDate + addDateEnd(formattedDate.takeLast(2).toInt())
}

fun addDateEnd(day: Int): String {
    return when {
        day in 11..20 -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }
}

