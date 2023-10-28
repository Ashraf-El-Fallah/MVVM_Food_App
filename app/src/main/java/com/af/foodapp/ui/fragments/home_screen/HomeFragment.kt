package com.af.foodapp.ui.fragments.home_screen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.databinding.FragmentHomeBinding
import com.af.foodapp.ui.activites.MealActivity
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getRandomMeal()
        observeRandomMealLiveData()
        onRandomMealClick()
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeRandomMealLiveData() {
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgRandomMeal)
        })
    }

    private fun initViewModel() {
        val homeRepository = HomeRepository()
        val viewModelFactory = HomeViewModelFactory(homeRepository)
        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

}