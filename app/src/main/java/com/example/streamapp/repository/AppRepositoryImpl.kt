package com.example.streamapp.repository

import com.example.streamapp.data.events.EventItem
import com.example.streamapp.data.schedule.ScheduleItem
import com.example.streamapp.network.NetworkApi
import io.reactivex.rxjava3.core.Observable

class AppRepositoryImpl(private val networkApi: NetworkApi): AppRepository  {

   override fun fetchListEvents(): Observable<List<EventItem>> {
      return networkApi.fetchListEvents()
   }

   override fun fetchListSchedule(): Observable<List<ScheduleItem>> {
      return networkApi.fetchListScheduled()
   }
}