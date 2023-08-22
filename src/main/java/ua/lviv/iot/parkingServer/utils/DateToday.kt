package ua.lviv.iot.parkingServer.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

object Util {
        private val DATE_FORMAT: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        fun getDateTodayInString(): String = DATE_FORMAT.format(Calendar.getInstance().time)
}