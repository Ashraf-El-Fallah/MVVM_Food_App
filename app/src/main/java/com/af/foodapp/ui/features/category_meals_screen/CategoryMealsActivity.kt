package com.af.foodapp.ui.features.category_meals_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.af.foodapp.data.repository.CategoryMealsRepository
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.databinding.ActivityCategoryMealsBinding
import com.af.foodapp.ui.features.meal_screen.MealActivity
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
        setUpCategoryMealsRecyclerView()
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

    private fun setUpCategoryMealsRecyclerView() {
        categoryMealsAdapter = CategoryMealAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun observeMealLiveData() {
        val categoryName = intent.getStringExtra(MealConstants.CATEGORY_NAME).toString()
        categoryMealsViewModel.getMealsByCategory(categoryName).observe(this) {
            binding.tvCategoryCount.text = "The number of meals : ${it.size.toString()}"
            categoryMealsAdapter.setMeals(it)
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