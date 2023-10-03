package com.example.streamapp.repository

import com.example.streamapp.data.events.EventItem
import com.example.streamapp.data.schedule.ScheduleItem
import io.reactivex.rxjava3.core.Observable

interface AppRepository {

   fun fetchListEvents(): Observable<List<EventItem>>

   fun fetchListSchedule(): Observable<List<ScheduleItem>>
}