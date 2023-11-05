package com.af.foodapp.ui.features.meal_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.data.repository.MealRepository
import com.af.foodapp.ui.features.home_screen.HomeViewModel

class MealViewModelFactory(
    private val mealRepository: MealRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealRepository) as T
    }
}
