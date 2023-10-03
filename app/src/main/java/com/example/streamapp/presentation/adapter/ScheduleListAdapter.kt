package com.example.streamapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.streamapp.data.schedule.ScheduleItem
import com.example.streamapp.databinding.EventItemBinding
import com.example.streamapp.utils.DateFormatter

class ScheduleListAdapter:
   ListAdapter<ScheduleItem, ScheduleListAdapter.ScheduleViewHolder>(ScheduleDiffCallback()) {

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): ScheduleViewHolder = ScheduleViewHolder(
      itemBinding = EventItemBinding.inflate(LayoutInflater.from(parent.context))
   )

   override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class ScheduleViewHolder(
      private val itemBinding: EventItemBinding
   ) : RecyclerView.ViewHolder(itemBinding.root) {

      fun bind(schedule: ScheduleItem) {
         with(itemBinding) {
            eventTitle.text = schedule.title
            eventSubtitle.text = schedule.subtitle
            eventDate.text = DateFormatter.formatDate(schedule.date)
            eventIcon.load(schedule.imageUrl)
         }
      }
   }
}

class ScheduleDiffCallback: DiffUtil.ItemCallback<ScheduleItem>() {
   override fun areItemsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem): Boolean {
      return oldItem.id == newItem.id
   }

   override fun areContentsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem): Boolean {
      return  oldItem.title == newItem.title &&
              oldItem.subtitle == newItem.subtitle &&
              oldItem.imageUrl == newItem.imageUrl &&
              oldItem.date == newItem.date
   }
}