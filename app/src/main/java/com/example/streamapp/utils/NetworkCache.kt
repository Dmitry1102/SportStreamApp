package com.example.streamapp.utils

import com.example.streamapp.data.events.EventItem

object NetworkCache {

   var cachedEventsList = mutableListOf<EventItem>()
}