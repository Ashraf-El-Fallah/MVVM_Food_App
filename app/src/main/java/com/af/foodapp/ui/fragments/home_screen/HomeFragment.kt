package com.af.foodapp.ui.fragments.home_screen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.databinding.FragmentHomeBinding
import com.af.foodapp.model.Meal
import com.af.foodapp.ui.activites.meal_screen.MealActivity
import com.af.foodapp.util.MealConstants
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal

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
            if (::randomMeal.isInitialized) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.putExtra(MealConstants.MEAL_ID, randomMeal.idMeal)
                intent.putExtra(MealConstants.MEAL_NAME, randomMeal.strMeal)
                intent.putExtra(MealConstants.MEAL_THUMB, randomMeal.strMealThumb)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Meal data is not available yet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeRandomMealLiveData() {
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = it
        })
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

}