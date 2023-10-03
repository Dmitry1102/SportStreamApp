package com.example.streamapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateFormatter {

   private const val MAIN_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"

   fun formatDate(datePattern: String): String {
      Log.d("DATE:", datePattern)

      val index = datePattern.indexOf("T")
      val dateStr = datePattern.substring(0,index)
      val timeStr = datePattern.substring(index+1, datePattern.length-1)

      val dateFull = "$dateStr $timeStr"
      Log.d("DATE:", dateFull)

      val dateFormat = SimpleDateFormat(MAIN_FORMAT, Locale.getDefault())
      val parsedDate = dateFormat.parse(dateFull) ?: return "Invalid timestamp"


      val currentDate = Calendar.getInstance()
      val givenDate = Calendar.getInstance().apply { time = parsedDate }

      val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())

      return when {
         isToday(currentDate, givenDate) -> "today; ${timeFormat.format(parsedDate)}"
         isYesterday(givenDate) -> "yesterday; ${timeFormat.format(parsedDate)}"
         isTomorrow(givenDate) -> "tomorrow; ${timeFormat.format(parsedDate)}"
         else -> SimpleDateFormat("dd.MM.yyyy; HH.mm", Locale.getDefault()).format(parsedDate)
      }
   }

   private fun isToday(currentDate: Calendar, givenDate: Calendar): Boolean {
      return currentDate.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR) &&
              currentDate.get(Calendar.MONTH) == givenDate.get(Calendar.MONTH) &&
              currentDate.get(Calendar.DAY_OF_MONTH) == givenDate.get(Calendar.DAY_OF_MONTH)
   }

   private fun isYesterday(givenDate: Calendar): Boolean {
      val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -1) }
      return yesterday.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR) &&
              yesterday.get(Calendar.MONTH) == givenDate.get(Calendar.MONTH) &&
              yesterday.get(Calendar.DAY_OF_MONTH) == givenDate.get(Calendar.DAY_OF_MONTH)
   }

   private fun isTomorrow(givenDate: Calendar): Boolean {
      val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }
      return tomorrow.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR) &&
              tomorrow.get(Calendar.MONTH) == givenDate.get(Calendar.MONTH) &&
              tomorrow.get(Calendar.DAY_OF_MONTH) == givenDate.get(Calendar.DAY_OF_MONTH)
   }
}