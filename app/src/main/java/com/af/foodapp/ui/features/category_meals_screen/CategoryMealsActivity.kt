package com.af.foodapp.ui.features.category_meals_screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.af.foodapp.data.repository.CategoryMealsRepository
import com.af.foodapp.data.source.Result
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.af.foodapp.databinding.ActivityCategoryMealsBinding
import com.af.foodapp.ui.adapters.CategoryMealAdapter
import com.af.foodapp.ui.features.meal_screen.MealActivity
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
        prepareCategoryMealsRecyclerView()
        observeMealLiveData()
    }

    private fun prepareCategoryMealsRecyclerView() {
        categoryMealsAdapter = CategoryMealAdapter {
            navigateToMealActivity(it)
        }
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun navigateToMealActivity(it: MealsByCategory) {
        val intent = Intent(applicationContext, MealActivity::class.java).apply {
            putExtra(MealConstants.MEAL_NAME, it.strMeal)
            putExtra(MealConstants.MEAL_ID, it.idMeal)
            putExtra(MealConstants.MEAL_THUMB, it.strMealThumb)
        }
        startActivity(intent)
    }


    private fun observeMealLiveData() {
        val categoryName = intent.getStringExtra(MealConstants.CATEGORY_NAME).toString()
        categoryMealsViewModel.getMealsByCategory(categoryName).observe(this) { result ->
            when (result) {
                Result.Loading -> binding.progressBar.isVisible = true

                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    binding.tvCategoryCount.text =
                        "The number of meals : ${result.data.size}"
                    categoryMealsAdapter.setMeals(result.data)
                }

                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        this,
                        result.throwable?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun initViewModel() {
        val categoryMealsRepository =
            CategoryMealsRepository(remoteDataSource = RetrofitInstance.api)
        val viewModelFactory = CategoryMealsViewModelFactory(categoryMealsRepository)
        categoryMealsViewModel =
            ViewModelProvider(this, viewModelFactory)[CategoryMealsViewModel::class.java]
    }
}