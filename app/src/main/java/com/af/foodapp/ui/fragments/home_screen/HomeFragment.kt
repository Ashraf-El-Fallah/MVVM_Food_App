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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.foodapp.databinding.FragmentHomeBinding
import com.af.foodapp.data.model.Meal
import com.af.foodapp.ui.activities.category_meals_screen.CategoryMealsActivity
import com.af.foodapp.ui.activities.meal_screen.MealActivity
import com.af.foodapp.ui.adapters.CategoriesListAdapter
import com.af.foodapp.ui.adapters.MostPopularMealsAdapter
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
        popularItemsAdapter = MostPopularMealsAdapter()
        categoriesListAdapter = CategoriesListAdapter()
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
        omPopularItemClick()

        prepareCategoriesRecyclerView()
        homeViewModel.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesListAdapter.onItemClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(MealConstants.CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesListAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        homeViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {
            categoriesListAdapter.setCategories(it)
        })
    }

    private fun omPopularItemClick() {
        popularItemsAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MealConstants.MEAL_NAME, it.strMeal)
            intent.putExtra(MealConstants.MEAL_ID, it.idMeal)
            intent.putExtra(MealConstants.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.rvPopularItems.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observerPopularItemsLiveData() {
        homeViewModel.observePopularItemsLiveData().observe(viewLifecycleOwner, Observer {
            popularItemsAdapter.setMeals(it as ArrayList)
        })
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