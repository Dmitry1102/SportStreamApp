package com.example.streamapp.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.streamapp.data.schedule.ScheduleItem
import com.example.streamapp.repository.AppRepository
import com.example.streamapp.utils.ListFormatter
import com.example.streamapp.utils.Resource
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ScheduleViewModelTest {

   private lateinit var appRepository: AppRepository
   private lateinit var listFormatter: ListFormatter<ScheduleItem>

   private lateinit var scheduleViewModel: ScheduleViewModel

   @Rule
   @JvmField
   val instantTaskExecutorRule = InstantTaskExecutorRule()


   @Before
   fun setUp() {
      appRepository = Mockito.mock()
      listFormatter = Mockito.mock()
   }

   @Test
   fun `getSchedulesList should post loading resource when events are fetched from repository`() {
      val fetchedSchedules = listOf(
         ScheduleItem("1", "2023-10-03T05:04:53.535Z", "aa.jpeg", "sub", "title"),
         ScheduleItem("2", "2023-10-03T05:04:53.535Z", "ab.jpeg", "sub1", "title1")
      )

      Mockito.`when`(appRepository.fetchListSchedule()).thenReturn(Observable.just(fetchedSchedules))

      scheduleViewModel = ScheduleViewModel(appRepository, listFormatter)

      scheduleViewModel.scheduleLiveData.value.let {
         if (it is Resource.Success) {
            it.data?.let { data ->
               Assert.assertTrue(data.isNotEmpty())
               Assert.assertEquals(data, fetchedSchedules
               )
            }
         }
      }
   }

   @Test
   fun `getSchedulesList should post error resource when there is an error fetching events from repository`() {
      val errorMessage = "Error fetching events"

      Mockito.`when`(appRepository.fetchListEvents()).thenReturn(Observable.error(Throwable(errorMessage)))

      scheduleViewModel = ScheduleViewModel(appRepository, listFormatter)

      scheduleViewModel.scheduleLiveData.value.let {
         if (it is Resource.Error) {
            it.message?.let { message ->
               Assert.assertTrue(message.isNotEmpty())
               Assert.assertEquals(message, errorMessage)
            }
         }
      }
   }
}