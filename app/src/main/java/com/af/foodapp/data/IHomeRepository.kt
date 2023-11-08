package com.af.foodapp.data

import androidx.lifecycle.LiveData
import com.af.foodapp.data.source.Result
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.data.source.remote.model.MealsByCategory

interface IHomeRepository {
    fun getRandomMeal(): LiveData<Result<Meal>>
    fun getPopularItems(): LiveData<Result<List<MealsByCategory>>>
    fun getCategories(): LiveData<Result<List<Category>>>
    fun getMealById(id: String): LiveData<Result<Meal?>>

    fun searchMeals(searchQuery: String): LiveData<Result<List<Meal>?>>
}