package com.af.foodapp.ui.features.favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.data.repository.FavoritesMealsRepository

class FavoritesViewModelFactory(
    private val favoritesMealsRepository: FavoritesMealsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModel(favoritesMealsRepository) as T
    }
}
