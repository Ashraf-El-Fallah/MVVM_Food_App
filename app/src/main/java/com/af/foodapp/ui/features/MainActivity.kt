package com.af.foodapp.ui.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.af.foodapp.data.repository.HomeRepository
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.databinding.ActivityMainBinding
import com.af.foodapp.ui.features.home_screen.HomeFragment
import com.af.foodapp.ui.features.home_screen.HomeViewModel
import com.af.foodapp.ui.features.home_screen.HomeViewModelFactory

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    //set up bottom navigation
    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.hostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController
        binding.btmNav.setupWithNavController(navController)
    }
}