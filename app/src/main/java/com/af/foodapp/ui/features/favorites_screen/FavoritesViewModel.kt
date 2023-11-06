package com.af.foodapp.ui.features.favorites_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.af.foodapp.data.IFavoritesMealsRepository
import com.af.foodapp.data.source.local.model.Meal

class FavoritesViewModel(
    private val favoritesMealRepository: IFavoritesMealsRepository
) : ViewModel() {
    fun observerFavoriteMealsLiveData(): LiveData<List<Meal>>? =
        favoritesMealRepository.getFavoritesMeals()
}