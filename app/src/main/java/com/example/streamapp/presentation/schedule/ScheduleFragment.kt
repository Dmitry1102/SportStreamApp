package com.example.streamapp.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.streamapp.databinding.FragmentScheduleBinding
import com.example.streamapp.presentation.BaseFragment
import com.example.streamapp.presentation.adapter.ScheduleListAdapter
import com.example.streamapp.utils.Resource
import com.example.streamapp.viewModel.ScheduleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleFragment : BaseFragment<FragmentScheduleBinding>() {

   private val viewModel: ScheduleViewModel by viewModel()

   private val scheduleListAdapter by lazy {
      ScheduleListAdapter()
   }

   override fun initBinding(
      inflater: LayoutInflater,
      container: ViewGroup
   ): FragmentScheduleBinding = FragmentScheduleBinding.inflate(inflater, container, false)

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      viewModel.getScheduleList()
      binding.scheduleList.adapter = scheduleListAdapter

      viewModel.scheduleLiveData.observe(viewLifecycleOwner) { state ->
         when(state) {
            is Resource.Success -> {
               binding.progressSchedules.visibility = View.INVISIBLE
               state.data?.let { eventItems ->
                  scheduleListAdapter.submitList(eventItems)
               }
            }
            is Resource.Error -> { showErrorDialog(state.message) }
            is Resource.Loading -> {
               binding.progressSchedules.visibility = View.VISIBLE
            }
         }
      }
   }
}