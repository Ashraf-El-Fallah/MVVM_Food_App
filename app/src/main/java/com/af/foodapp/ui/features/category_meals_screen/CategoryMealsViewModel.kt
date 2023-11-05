package com.af.foodapp.ui.features.category_meals_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af.foodapp.data.ICategoryMealsRepository
import com.af.foodapp.data.repository.CategoryMealsRepository
import com.af.foodapp.data.source.remote.model.MealsByCategory

class CategoryMealsViewModel(
    private val categoryMealsRepository: CategoryMealsRepository
) : ViewModel() {
    //use live data to update meals when click on category
    private var mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> = mealsLiveData

    fun getMealsByCategory(categoryName: String) {
        mealsLiveData = categoryMealsRepository.getMealsByCategory(categoryName)
    }

}