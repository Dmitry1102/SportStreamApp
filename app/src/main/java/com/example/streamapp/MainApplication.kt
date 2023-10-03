package com.example.streamapp

import android.app.Application
import com.example.streamapp.modules.formatterModule
import com.example.streamapp.modules.repositoryModule
import com.example.streamapp.modules.retrofitModule
import com.example.streamapp.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

   override fun onCreate() {
      super.onCreate()
      startKoin {
         androidContext(this@MainApplication)
         modules(listOf(
            retrofitModule,
            repositoryModule,
            formatterModule,
            viewModelModule
         ))
      }
   }
}