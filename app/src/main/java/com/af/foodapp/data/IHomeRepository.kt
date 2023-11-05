package com.af.foodapp.data

import androidx.lifecycle.LiveData
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.model.CategoryList
import com.af.foodapp.data.source.local.model.MealList
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import retrofit2.Call

interface IHomeRepository {
    fun getRandomMeal(): Call<MealList>
    fun getPopularItems(): Call<MealsByCategoryList>
    fun getCategories(): Call<CategoryList>
    fun getFavoritesMeals(): LiveData<List<Meal>>
}