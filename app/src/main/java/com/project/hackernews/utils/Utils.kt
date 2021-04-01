package com.project.hackernews.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object{
       @SuppressLint("SimpleDateFormat")
       fun dateFormatToShow(date: String) : String {
           return try {
               val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
               val newDate = format.parse(date)
               DateFormat.getDateInstance(DateFormat.FULL).format(newDate!!)
           } catch (e: Exception) {
               DateFormat.getDateInstance(DateFormat.FULL).format(Date())
           }
       }
        @SuppressLint("SimpleDateFormat")
        fun dateToLong(date: String) : Long {
            return try {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val newDate = format.parse(date)
                newDate.time
            } catch (e: Exception) {
                0L
            }
        }

        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnected == true
            return isConnected
        }
    }
}