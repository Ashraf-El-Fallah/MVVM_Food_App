package com.af.foodapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.local.model.MealList
import retrofit2.Call

interface IMealRepository {
    fun getMealDetails(id: String): MutableLiveData<Meal>
    suspend fun insertMeal(meal: Meal)

    fun getFavoritesMeals(): LiveData<List<Meal>>?

    suspend fun deleteMeal(meal: Meal): Unit?
}