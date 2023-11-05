package com.af.foodapp.ui.features.category_meals_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.data.ICategoryMealsRepository
import com.af.foodapp.data.repository.CategoryMealsRepository
import com.af.foodapp.ui.features.home_screen.HomeViewModel

class CategoryMealsViewModelFactory(
    private val categoryMealsRepository: CategoryMealsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryMealsViewModel(categoryMealsRepository) as T
    }
}