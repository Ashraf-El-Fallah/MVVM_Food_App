package com.af.foodapp.data

import androidx.lifecycle.LiveData
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.data.source.remote.model.MealsByCategory

interface IHomeRepository {
    fun getRandomMeal(): LiveData<Meal>
    fun getPopularItems(): LiveData<List<MealsByCategory>>
    fun getCategories(): LiveData<List<Category>>
    fun getMealById(id: String): LiveData<Meal?>

    fun searchMeals(searchQuery: String): LiveData<List<Meal>?>
}