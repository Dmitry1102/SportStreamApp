package com.example.streamapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.streamapp.data.events.EventItem
import com.example.streamapp.databinding.EventItemBinding
import com.example.streamapp.utils.DateFormatter

class EventsListAdapter(
   private val onItemClickListener: OnClickListener
): ListAdapter<EventItem, EventsListAdapter.EventsViewHolder>(EventsDiffCallback()) {

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): EventsViewHolder = EventsViewHolder(
       itemBinding = EventItemBinding.inflate(LayoutInflater.from(parent.context)),
       onItemClickListener = onItemClickListener
   )

   override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
      holder.bind(getItem(position))
   }

   inner class EventsViewHolder(
      private val itemBinding: EventItemBinding,
      private val onItemClickListener: OnClickListener
   ) : RecyclerView.ViewHolder(itemBinding.root) {

      fun bind(event: EventItem) {
         with(itemBinding) {
            eventTitle.text = event.title
            eventSubtitle.text = event.subtitle
            eventDate.text = DateFormatter.formatDate(event.date)
            eventIcon.load(event.imageUrl)
            eventItem.setOnClickListener {
               onItemClickListener.onItemClick(event.videoUrl)
            }
         }
      }
   }
}

class EventsDiffCallback: DiffUtil.ItemCallback<EventItem>() {
   override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
      return oldItem.id == newItem.id
   }

   override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
      return  oldItem.title == newItem.title &&
              oldItem.subtitle == newItem.subtitle &&
              oldItem.imageUrl == newItem.imageUrl &&
              oldItem.date == newItem.date
   }
}