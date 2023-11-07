package com.af.foodapp.data

import androidx.lifecycle.LiveData
import com.af.foodapp.data.source.local.model.Meal

interface IMealRepository {
    fun getMealDetails(id: String): LiveData<Meal>
    suspend fun upsertMeal(meal: Meal)
    suspend fun deleteMeal(meal: Meal)
}