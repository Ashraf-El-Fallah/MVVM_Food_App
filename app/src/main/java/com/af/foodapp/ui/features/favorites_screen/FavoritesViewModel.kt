package com.af.foodapp.ui.features.favorites_screen

import androidx.lifecycle.ViewModel
import com.af.foodapp.data.IFavoritesMealsRepository

class FavoritesViewModel(
    private val favoritesMealRepository: IFavoritesMealsRepository
) : ViewModel() {
    fun observerFavoriteMealsLiveData() = favoritesMealRepository.getFavoritesMeals()
}