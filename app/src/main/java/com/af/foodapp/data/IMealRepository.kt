package com.af.foodapp.data

import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.local.model.MealList
import retrofit2.Call

interface IMealRepository {
    fun getMealDetails(id: String): Call<MealList>
    suspend fun insertMeal(meal: Meal): Unit?

    suspend fun deleteMeal(meal: Meal) : Unit?
}