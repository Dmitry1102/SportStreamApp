package com.example.streamapp.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.streamapp.R

abstract class BaseFragment<T : ViewBinding> : Fragment() {

   private var _viewBinding: T? = null
   protected val binding get() = _viewBinding!!

   protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup): T?

   protected fun showErrorDialog(errorMessage: String?) {
      val builder = AlertDialog.Builder(requireContext())
         .setTitle(getString(R.string.error_label))
         .setMessage(errorMessage ?: getString(R.string.default_error_message))

      val alertDialog = builder.create()
      alertDialog.show()
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      _viewBinding = initBinding(inflater, container!!)
      return binding.root
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _viewBinding = null
   }
}