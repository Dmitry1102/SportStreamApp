package com.example.streamapp.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.streamapp.data.events.EventItem
import com.example.streamapp.repository.AppRepository
import com.example.streamapp.utils.ListFormatter
import com.example.streamapp.utils.Resource
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventsViewModelTest {

   private lateinit var appRepository: AppRepository
   private lateinit var listFormatter: ListFormatter<EventItem>

   private lateinit var eventsViewModel: EventsViewModel

   @Rule
   @JvmField
   val instantTaskExecutorRule = InstantTaskExecutorRule()


   @Before
   fun setUp() {
      appRepository = mock()
      listFormatter = mock()
   }

   @Test
   fun `getEventsList should post success resource`() {
      val cachedEvents = listOf(
         EventItem("1", "2023-10-03T05:04:53.535Z", "aa.jpeg", "sub", "title", "aa.mp4"),
         EventItem("2", "2023-10-03T05:04:53.535Z", "ab.jpeg", "sub1", "title1", "ab.mp4")
      )

      Mockito.`when`(appRepository.fetchListEvents()).thenReturn(Observable.just(cachedEvents))

      eventsViewModel = EventsViewModel(appRepository, listFormatter)

      eventsViewModel.eventsLiveData.value.let {
         if (it is Resource.Success) {
            it.data?.let { data ->
               assertTrue(data.isNotEmpty())
               assertEquals(data, cachedEvents)
            }
         }
      }
   }

   @Test
   fun `getEventsList should post error resource when there is an error fetching events from repository`() {
      val errorMessage = "Error fetching events"

      Mockito.`when`(appRepository.fetchListEvents()).thenReturn(Observable.error(Throwable(errorMessage)))

      eventsViewModel = EventsViewModel(appRepository, listFormatter)

      eventsViewModel.eventsLiveData.value.let {
         if (it is Resource.Error) {
            it.message?.let { message ->
               assertTrue(message.isNotEmpty())
               assertEquals(message, errorMessage)
            }
         }
      }
   }
}