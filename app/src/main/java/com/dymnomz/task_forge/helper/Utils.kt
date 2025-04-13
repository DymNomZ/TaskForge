package com.dymnomz.task_forge.helper

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDate() : String {

    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(date)
}

fun convertMonth(month: String) : String {

    return when (month) {
        "1" -> "January"
        "2" -> "February"
        "3" -> "March"
        "4" -> "April"
        "5" -> "May"
        "6" -> "June"
        "7" -> "July"
        "8" -> "August"
        "9" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> "Invalid Month"
    }

}