package com.example.streamapp.presentation.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.streamapp.databinding.FragmentEventsBinding
import com.example.streamapp.presentation.BaseFragment
import com.example.streamapp.presentation.adapter.EventsListAdapter
import com.example.streamapp.presentation.adapter.OnClickListener
import com.example.streamapp.utils.Resource
import com.example.streamapp.viewModel.EventsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsFragment : BaseFragment<FragmentEventsBinding>(), OnClickListener {

   private val viewModel: EventsViewModel by viewModel()

   private val eventsListAdapter by lazy {
      EventsListAdapter(this)
   }

   override fun initBinding(
      inflater: LayoutInflater,
      container: ViewGroup
   ): FragmentEventsBinding = FragmentEventsBinding.inflate(inflater, container, false)

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      viewModel.getEventsList()
      binding.eventsList.adapter = eventsListAdapter

      viewModel.eventsLiveData.observe(viewLifecycleOwner) { state ->
         when(state) {
            is Resource.Success -> {
               state.data?.let { eventItems ->
                  binding.progressEvents.visibility = View.GONE
                  eventsListAdapter.submitList(eventItems)
               }
            }
            is Resource.Error -> { showErrorDialog(state.message) }
            is Resource.Loading -> { binding.progressEvents.visibility = View.VISIBLE }
          }
      }
   }

   override fun onItemClick(videoUrl: String) {
      val direction = EventsFragmentDirections.actionEventsFragmentToPlayBackFragment(videoUrl)
      findNavController().navigate(direction)
   }
}