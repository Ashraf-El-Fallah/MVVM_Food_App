package com.af.foodapp.data

import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import retrofit2.Call

interface ICategoryMealsRepository {
    fun getMealsByCategory(categoryName: String): Call<MealsByCategoryList>
}