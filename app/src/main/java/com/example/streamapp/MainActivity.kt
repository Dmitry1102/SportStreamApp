package com.example.streamapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.streamapp.databinding.MainActivityBinding

class MainActivity: AppCompatActivity() {

   private var _binding: MainActivityBinding? = null
   private val binding: MainActivityBinding get() = _binding!!

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      _binding = MainActivityBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val bottomNavigationView = binding.bottomNavigationView
      val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
      val navController = navHostFragment.navController
      setupWithNavController(bottomNavigationView, navController)
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }
}