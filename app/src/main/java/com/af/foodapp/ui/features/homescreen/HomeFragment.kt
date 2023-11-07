package com.af.foodapp.ui.features.homescreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.foodapp.databinding.FragmentHomeBinding
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.af.foodapp.ui.features.MainActivity
import com.af.foodapp.ui.features.adapters.CategoriesListAdapter
import com.af.foodapp.ui.features.adapters.MostPopularMealsAdapter
import com.af.foodapp.ui.features.categorymeals.CategoryMealsActivity
import com.af.foodapp.ui.features.mealscreen.MealActivity
import com.af.foodapp.util.MealConstants
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularMealsAdapter
    private lateinit var categoriesListAdapter: CategoriesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        popularItemsAdapter = MostPopularMealsAdapter { clickedMeal ->
            navigateToMealDetails(clickedMeal)
        }
        categoriesListAdapter = CategoriesListAdapter { clickedCategory ->
            navigateToCategoryMeals(clickedCategory)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeViewModel.getRandomMeal()
        observeRandomMealLiveData()
        onRandomMealClick()

        homeViewModel.getPopularItems()
        observerPopularItemsLiveData()

        prepareCategoriesRecyclerView()
        homeViewModel.getCategories()
        observerCategoriesLiveData()
    }

    private fun navigateToMealDetails(clickedMeal: MealsByCategory) {
        val intent = Intent(activity, MealActivity::class.java)
        intent.putExtra(MealConstants.MEAL_NAME, clickedMeal.strMeal)
        intent.putExtra(MealConstants.MEAL_ID, clickedMeal.idMeal)
        intent.putExtra(MealConstants.MEAL_THUMB, clickedMeal.strMealThumb)
        startActivity(intent)
    }

    private fun navigateToCategoryMeals(clickedCategory: Category) {
        val intent = Intent(activity, CategoryMealsActivity::class.java)
        intent.putExtra(MealConstants.CATEGORY_NAME, clickedCategory.strCategory)
        startActivity(intent)
    }

    private fun prepareCategoriesRecyclerView() {
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesListAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        homeViewModel.getCategories().observe(viewLifecycleOwner) { categoriesList ->
            categoriesListAdapter.differ.submitList(categoriesList)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.rvPopularItems.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observerPopularItemsLiveData() {
        homeViewModel.getPopularItems().observe(viewLifecycleOwner) { mostPopularMeals ->
            popularItemsAdapter.differ.submitList(mostPopularMeals as ArrayList)
        }
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
                Toast.makeText(context, "Meal data is not available yet", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun observeRandomMealLiveData() {
        homeViewModel.getRandomMeal().observe(viewLifecycleOwner) { randomMeal ->
            Glide.with(this)
                .load(randomMeal.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = randomMeal
        }
    }


    private fun initViewModel() {
        homeViewModel = (activity as MainActivity).viewModel
    }
}
