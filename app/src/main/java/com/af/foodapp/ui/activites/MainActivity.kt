package com.af.foodapp.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.af.foodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initBottomNavigation()

    }

    private fun initBottomNavigation(){
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.hostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController
        binding.btmNav.setupWithNavController(navController)
    }
}