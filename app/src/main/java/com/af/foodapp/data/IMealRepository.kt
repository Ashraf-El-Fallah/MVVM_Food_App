package com.af.foodapp.data

import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.source.local.model.Meal

interface IMealRepository {
    fun getMealDetails(id: String): MutableLiveData<Meal>
    suspend fun upsertMeal(meal: Meal)
    suspend fun deleteMeal(meal: Meal): Unit?
}