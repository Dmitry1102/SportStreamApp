package com.example.streamapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.streamapp.data.schedule.ScheduleItem
import com.example.streamapp.repository.AppRepository
import com.example.streamapp.utils.ListFormatter
import com.example.streamapp.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ScheduleViewModel(
   private val appRepository: AppRepository,
   private val listFormatter: ListFormatter<ScheduleItem>
): BaseViewModel() {

   private var _scheduleLiveData = MutableLiveData<Resource<List<ScheduleItem>>>()
   val scheduleLiveData: LiveData<Resource<List<ScheduleItem>>> get() = _scheduleLiveData

   fun getScheduleList() {
      _scheduleLiveData.postValue(Resource.Loading())
      val observable = Observable.concat(appRepository.fetchListSchedule(),
         Observable.interval(30,TimeUnit.SECONDS).flatMap { appRepository.fetchListSchedule() })
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())

      disposable = observable.subscribe ({ schedules ->
         _scheduleLiveData.postValue(Resource.Success(listFormatter.sortList(schedules){scheduleItem -> scheduleItem.date})
      )},{
         error -> _scheduleLiveData.postValue(Resource.Error( error.message.toString()))
      })
   }


}