package com.af.foodapp.ui.features.favoritemeals

import androidx.lifecycle.ViewModel
import com.af.foodapp.data.IFavoritesMealsRepository

class FavoriteMealsViewModel(
    private val favoritesMealRepository: IFavoritesMealsRepository
) : ViewModel() {
    fun observerFavoriteMealsLiveData() = favoritesMealRepository.getFavoritesMeals()
}