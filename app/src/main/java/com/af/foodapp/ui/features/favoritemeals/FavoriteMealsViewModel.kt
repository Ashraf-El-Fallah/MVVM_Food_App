package com.af.foodapp.ui.features.favoritemeals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.foodapp.data.IFavoritesMealsRepository
import com.af.foodapp.data.IMealRepository
import com.af.foodapp.data.source.local.model.Meal
import kotlinx.coroutines.launch

class FavoriteMealsViewModel(
    private val favoritesMealRepository: IFavoritesMealsRepository,
    private val mealRepository: IMealRepository
) : ViewModel() {
    fun observerFavoriteMealsLiveData() = favoritesMealRepository.getFavoritesMeals()

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            favoritesMealRepository.deleteMeal(meal)
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.upsertMeal(meal)
        }
    }
}