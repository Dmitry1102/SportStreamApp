package com.example.streamapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.streamapp.data.events.EventItem
import com.example.streamapp.repository.AppRepository
import com.example.streamapp.utils.ListFormatter
import com.example.streamapp.utils.NetworkCache
import com.example.streamapp.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class EventsViewModel(
   private val appRepository: AppRepository,
   private val listFormatter: ListFormatter<EventItem>
): BaseViewModel() {

   private var _eventsLiveData = MutableLiveData<Resource<List<EventItem>>>()
   val eventsLiveData: LiveData<Resource<List<EventItem>>> get() = _eventsLiveData

   fun getEventsList() {
      val cachedList = NetworkCache.cachedEventsList

      if (cachedList.isNotEmpty()) {
         _eventsLiveData.postValue(Resource.Success(listFormatter.sortList(cachedList) { eventItem -> eventItem.date }))
         return
      }

      _eventsLiveData.postValue(Resource.Loading())
      disposable = appRepository.fetchListEvents()
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe({ events ->
            _eventsLiveData.postValue(Resource.Success(listFormatter.sortList(events) { eventItem -> eventItem.date }))
            fetchCache(events)
         },{ error ->
            _eventsLiveData.postValue(Resource.Error(error.message.toString()))
         })
   }

   private fun fetchCache(events: List<EventItem>) {
      NetworkCache.cachedEventsList.clear()
      NetworkCache.cachedEventsList.addAll(elements = events)
   }
}