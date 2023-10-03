package com.example.streamapp.modules

import com.example.streamapp.data.events.EventItem
import com.example.streamapp.data.schedule.ScheduleItem
import com.example.streamapp.network.NetworkApi
import com.example.streamapp.repository.AppRepository
import com.example.streamapp.repository.AppRepositoryImpl
import com.example.streamapp.utils.ListFormatter
import com.example.streamapp.viewModel.EventsViewModel
import com.example.streamapp.viewModel.ScheduleViewModel
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://us-central1-dazn-sandbox.cloudfunctions.net"

val retrofitModule = module { single {
      Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
         .client(OkHttpClient.Builder().build())
         .build()
         .create(NetworkApi::class.java)
   }
}

val repositoryModule = module {
   single<AppRepository> { AppRepositoryImpl(get()) }
}

val formatterModule = module {
   single { ListFormatter<List<EventItem>>() }
   single { ListFormatter<List<ScheduleItem>>() }
}

val viewModelModule = module {
   viewModel { ScheduleViewModel(get(),get()) }
   viewModel { EventsViewModel(get(),get()) }
}