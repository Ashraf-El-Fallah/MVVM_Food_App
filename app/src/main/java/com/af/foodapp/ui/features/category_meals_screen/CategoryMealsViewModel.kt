package com.af.foodapp.ui.features.category_meals_screen

import androidx.lifecycle.ViewModel
import com.af.foodapp.data.ICategoryMealsRepository

class CategoryMealsViewModel(
    private val categoryMealsRepository: ICategoryMealsRepository
) : ViewModel() {
    fun getMealsByCategory(categoryName: String) =
        categoryMealsRepository.getMealsByCategory(categoryName)
}