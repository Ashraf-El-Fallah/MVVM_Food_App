package com.af.foodapp.ui.activities.category_meals_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.af.foodapp.databinding.ActivityCategoryMealsBinding
import com.af.foodapp.ui.activities.meal_screen.MealActivity
import com.af.foodapp.ui.adapters.CategoryMealAdapter
import com.af.foodapp.util.MealConstants

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        getTheCategoryName()
        prepareCategoryMealsRecyclerView()
        observeMealLiveData()
        onMealClick()
    }

    private fun onMealClick() {
        categoryMealsAdapter.onItemClick = {
            val intent = Intent(applicationContext, MealActivity::class.java)
            intent.putExtra(MealConstants.MEAL_NAME, it.strMeal)
            intent.putExtra(MealConstants.MEAL_ID, it.idMeal)
            intent.putExtra(MealConstants.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareCategoryMealsRecyclerView() {
        categoryMealsAdapter = CategoryMealAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun getTheCategoryName() {
        intent.getStringExtra(MealConstants.CATEGORY_NAME)
            ?.let { categoryMealsViewModel.getMealsByCategory(it) }
    }

    private fun observeMealLiveData() {
        categoryMealsViewModel.observeMealLiveData().observe(this, Observer {
            binding.tvCategoryCount.text = "The number of meals : ${it.size.toString()}"
            categoryMealsAdapter.setMeals(it)
        })
    }


    private fun initViewModel() {
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
    }
}