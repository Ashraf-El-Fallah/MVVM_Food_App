package com.af.foodapp.ui.features.category_meals_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.af.foodapp.data.repository.CategoryMealsRepository
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.databinding.ActivityCategoryMealsBinding
import com.af.foodapp.ui.features.meal_screen.MealActivity
import com.af.foodapp.ui.adapters.CategoryMealAdapter
import com.af.foodapp.util.MealConstants

//this activity have the recycler view of meals of the category you choose
//when you click any meal you can show everything about this meal like the steps to cook it
class CategoryMealsActivity : AppCompatActivity() {

    //initialization for binding,View Model and adapter using in this activity
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

    //use to pass information of the meal to meal activity and start it
    private fun onMealClick() {
        categoryMealsAdapter.onItemClick = {
            val intent = Intent(applicationContext, MealActivity::class.java)
            intent.putExtra(MealConstants.MEAL_NAME, it.strMeal)
            intent.putExtra(MealConstants.MEAL_ID, it.idMeal)
            intent.putExtra(MealConstants.MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    //use to prepare recycler view for meals
    private fun prepareCategoryMealsRecyclerView() {
        categoryMealsAdapter = CategoryMealAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    //pass the category name to the view model to pass for api to get meals of this category
    private fun getTheCategoryName() {
        intent.getStringExtra(MealConstants.CATEGORY_NAME)
            ?.let { categoryMealsViewModel.getMealsByCategory(it) }
    }

    //observe meals to update the recycler view and set new data and number of meals
    private fun observeMealLiveData() {
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer {
            binding.tvCategoryCount.text = "The number of meals : ${it.size.toString()}"
            categoryMealsAdapter.setMeals(it)
        })
    }

    //initialization for view model
    private fun initViewModel() {
        val categoryMealsRepository =
            CategoryMealsRepository(remoteDataSource = RetrofitInstance.api)
        val viewModelFactory = CategoryMealsViewModelFactory(categoryMealsRepository)
        categoryMealsViewModel =
            ViewModelProvider(this, viewModelFactory)[CategoryMealsViewModel::class.java]
    }
}