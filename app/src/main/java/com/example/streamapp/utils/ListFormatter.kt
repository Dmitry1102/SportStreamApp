package com.example.streamapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

class ListFormatter<T> {

   private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)

   fun sortList(list: List<T>, dateSelector: (T) -> String): List<T> {
      return list.sortedBy { dateFormat.parse(dateSelector(it)) }
   }

}