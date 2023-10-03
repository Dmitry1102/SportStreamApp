package com.example.streamapp.network

import com.example.streamapp.data.events.EventItem
import com.example.streamapp.data.schedule.ScheduleItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface NetworkApi {

   @GET("/getEvents")
   fun fetchListEvents(): Observable<List<EventItem>>

   @GET("/getSchedule")
   fun fetchListScheduled(): Observable<List<ScheduleItem>>
}