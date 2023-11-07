package com.af.foodapp.ui.features.meal_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.af.foodapp.data.IMealRepository
import com.af.foodapp.data.source.local.model.Meal
import kotlinx.coroutines.launch

class MealViewModel(
    private val mealRepository: IMealRepository
) : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    fun getMealDetail(id: String) {
        mealDetailsLiveData = mealRepository.getMealDetails(id)
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.upsertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.deleteMeal(meal)
        }
    }

    //to observe live data in activity and use abstraction
    fun observerMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }
}