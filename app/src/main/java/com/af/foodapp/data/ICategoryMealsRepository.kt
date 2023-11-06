package com.af.foodapp.data

import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.source.remote.model.MealsByCategory


interface ICategoryMealsRepository {
    fun getMealsByCategory(categoryName: String): MutableLiveData<List<MealsByCategory>>
}