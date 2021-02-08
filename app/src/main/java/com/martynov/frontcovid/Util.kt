package com.martynov.frontcovid

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convecrStringToDate(dateString: String): Calendar {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date: Date = parser.parse(dateString)
    var calendar = Calendar.getInstance()
    calendar.timeInMillis = date.time
    return calendar

}
fun convecrDateToString(cal : Calendar): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date = Date(cal.timeInMillis)
    val text = parser.format(date)
    return text

}



fun convertMount(mount: Int): String {
    when (mount) {
        0 -> {
            return "Января"
        }
        1 -> {
            return "Февраля"
        }
        2 -> {
            return "Марта"
        }
        3 -> {
            return "Апреля"
        }
        4 -> {
            return "Майя"
        }
        5 -> {
            return "Июня"
        }
        6 -> {
            return "Июля"
        }
        7 -> {
            return "Августа"
        }
        8 -> {
            return "Сентебря"
        }
        9 -> {
            return "Октября"
        }
        10 -> {
            return "Ноября"
        }
        11 -> {
            return "Декабря"
        }
        else -> {
            return ""
        }

    }
}