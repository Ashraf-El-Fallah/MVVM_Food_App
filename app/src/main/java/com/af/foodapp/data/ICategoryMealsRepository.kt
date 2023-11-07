package com.af.foodapp.data

import androidx.lifecycle.LiveData
import com.af.foodapp.data.source.remote.model.MealsByCategory


interface ICategoryMealsRepository {
    fun getMealsByCategory(categoryName: String): LiveData<List<MealsByCategory>>
}