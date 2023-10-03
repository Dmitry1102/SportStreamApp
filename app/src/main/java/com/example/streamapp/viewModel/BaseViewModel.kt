package com.example.streamapp.viewModel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel: ViewModel() {

   protected var disposable: Disposable? = null

   override fun onCleared() {
      super.onCleared()
      disposable?.dispose()
   }
}