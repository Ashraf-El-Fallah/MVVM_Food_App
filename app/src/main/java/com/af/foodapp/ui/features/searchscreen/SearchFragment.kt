package com.af.foodapp.ui.features.searchscreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.af.foodapp.data.source.Result
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.databinding.FragmentSearchBinding
import com.af.foodapp.ui.features.MainActivity
import com.af.foodapp.ui.features.adapters.MealsAdapter
import com.af.foodapp.ui.features.homescreen.HomeViewModel
import com.af.foodapp.ui.features.mealscreen.MealActivity
import com.af.foodapp.util.MealConstants
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerViewAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setOnClickSearchIcon()

    }

    private fun setOnClickSearchIcon() {
        binding.icSearch.setOnClickListener {
            searchForMeals()
        }
    }

    /////////////////////////////////
    private fun searchWhenWriting() {
        var searchJob: Job? = null
        binding.edSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
//                delay(500)
                viewModel.searchMeals(searchQuery.toString())
            }
        }
    }

    private fun searchForMeals() {
        val searchQuery = binding.edSearch.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery).observe(viewLifecycleOwner) { searchedMeals ->
                when (searchedMeals) {
                    Result.Loading -> binding.progressBar.isVisible = true

                    is Result.Success -> {
                        binding.progressBar.isVisible = false
                        searchRecyclerViewAdapter.differ.submitList(searchedMeals.data)
                    }

                    is Result.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            context,
                            searchedMeals.throwable?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        } else
            Toast.makeText(context, "Enter the meal you want to search about!", Toast.LENGTH_SHORT)
                .show()
    }

    private fun setUpRecyclerView() {
        searchRecyclerViewAdapter = MealsAdapter { clickedMeal ->
            navigateToMealDetails(clickedMeal)
        }
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
            adapter = searchRecyclerViewAdapter
        }
    }

    private fun navigateToMealDetails(clickedMeal: Meal) {
        val intent = Intent(activity, MealActivity::class.java)
        intent.apply {
            putExtra(MealConstants.MEAL_NAME, clickedMeal.strMeal)
            putExtra(MealConstants.MEAL_ID, clickedMeal.idMeal)
            putExtra(MealConstants.MEAL_THUMB, clickedMeal.strMealThumb)
        }
        startActivity(intent)
    }

    private fun initViewModel() {
        viewModel = (activity as MainActivity).viewModel
    }
}