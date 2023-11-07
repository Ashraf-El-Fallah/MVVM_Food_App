package com.af.foodapp.ui.features.favoritemeals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.data.IMealRepository
import com.af.foodapp.data.repository.FavoritesMealsRepository

class FavoriteMealsViewModelFactory(
    private val favoritesMealsRepository: FavoritesMealsRepository,
    private val mealRepository: IMealRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteMealsViewModel(favoritesMealsRepository, mealRepository) as T
    }
}
