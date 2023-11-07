package com.af.foodapp.ui.features.categorymeals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.data.repository.CategoryMealsRepository

class CategoryMealsViewModelFactory(
    private val categoryMealsRepository: CategoryMealsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryMealsViewModel(categoryMealsRepository) as T
    }
}