package com.example.streamapp.presentation.playback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.navigation.fragment.navArgs
import com.example.streamapp.databinding.FragmentPlaybackBinding
import com.example.streamapp.presentation.BaseFragment

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class PlayBackFragment : BaseFragment<FragmentPlaybackBinding>() {

   private val args: PlayBackFragmentArgs by navArgs()

   private var exoPlayer: ExoPlayer? = null
   private var savedPosition: Long = 0

   override fun initBinding(
      inflater: LayoutInflater,
      container: ViewGroup
   ): FragmentPlaybackBinding = FragmentPlaybackBinding.inflate(inflater, container, false)

   override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
   ) {
      super.onViewCreated(view, savedInstanceState)
      val videoUrl = args.playbackUrl

      exoPlayer = ExoPlayer.Builder(requireContext()).build()
      binding.playerView.player = exoPlayer

      val mediaItem = setMediaItem(videoUrl)
      val mediaSource = ProgressiveMediaSource.Factory(
         DefaultDataSource.Factory(requireContext())
      ).createMediaSource(mediaItem)

      if(savedInstanceState != null) {
         savedPosition = savedInstanceState.getLong(SAVED_PLAYER_POSITION)
      }

      exoPlayer?.apply {
         setMediaSource(mediaSource)
         playWhenReady = true
         seekTo(savedPosition)
         prepare()
         play()
      }
   }

   override fun onSaveInstanceState(outState: Bundle) {
      super.onSaveInstanceState(outState)
      exoPlayer?.let { outState.putLong(SAVED_PLAYER_POSITION, it.currentPosition) }
   }

   override fun onDestroyView() {
      super.onDestroyView()
      exoPlayer?.release()
   }

   private fun setMediaItem(videoUrl: String) : MediaItem = MediaItem.Builder()
               .setUri(videoUrl)
               .setMimeType(MimeTypes.APPLICATION_MP4)
               .build()

   companion object {
      private const val SAVED_PLAYER_POSITION = "player_position"
   }
}