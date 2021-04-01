package com.project.hackernews.utils

import android.annotation.SuppressLint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

class KMoment {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun dateToLong(date: String) : Long {
            return try {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
                val newDate = format.parse(date)
                newDate.time
            } catch (e: Exception) {
                0L
            }
        }

        fun getTimeAgo(create_At: String): String {

            val timeValue = dateToLong(create_At)

            var time = timeValue
            if (time < 1000000000000L) {
                // if timestamp given in seconds, convert to millis
                time *= 1000
            }
            val now: Long = Calendar.getInstance().time.time
            if (time > now || time <= 0) {
                return "invalid date"
            }

            // TODO: localize
            val diff = now - time
            return if (diff < MINUTE_MILLIS) {
                "just now"
            } else if (diff < 2 * MINUTE_MILLIS) {
                "1m"
            } else if (diff < 50 * MINUTE_MILLIS) {
                "${diff / MINUTE_MILLIS.toLong()}m"
            } else if (diff < 90 * MINUTE_MILLIS) {
                "1h"
            } else if (diff < 24 * HOUR_MILLIS) {
                "${diff / HOUR_MILLIS.toLong()}h"
            } else if (diff < 48 * HOUR_MILLIS) {
                "Yesterday"
            } else {
                "${diff / DAY_MILLIS.toLong()} days ago"
            }
        }
    }
}