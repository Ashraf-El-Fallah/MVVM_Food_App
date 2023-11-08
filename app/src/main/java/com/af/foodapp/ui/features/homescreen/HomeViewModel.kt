package com.af.foodapp.ui.features.homescreen

import androidx.lifecycle.ViewModel
import com.af.foodapp.data.IHomeRepository

class HomeViewModel(
    private val homeRepository: IHomeRepository
) : ViewModel() {
    init {
        getRandomMeal()
    }
    fun getRandomMeal() = homeRepository.getRandomMeal()

    fun getPopularItems() = homeRepository.getPopularItems()

    fun getCategories() = homeRepository.getCategories()

    fun getMealById(id: String) = homeRepository.getMealById(id)

    fun searchMeals(searchQuery: String) = homeRepository.searchMeals(searchQuery)
}