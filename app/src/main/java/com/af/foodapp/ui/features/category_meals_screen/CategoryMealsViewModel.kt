package com.af.foodapp.ui.features.category_meals_screen

import androidx.lifecycle.ViewModel
import com.af.foodapp.data.repository.CategoryMealsRepository

class CategoryMealsViewModel(
    private val categoryMealsRepository: CategoryMealsRepository
) : ViewModel() {
    fun getMealsByCategory(categoryName: String) =
        categoryMealsRepository.getMealsByCategory(categoryName)


}