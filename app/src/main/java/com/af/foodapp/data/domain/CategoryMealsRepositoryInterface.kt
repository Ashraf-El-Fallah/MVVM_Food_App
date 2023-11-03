package com.af.foodapp.data.domain

import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import retrofit2.Call

interface CategoryMealsRepositoryInterface {
    fun getMealsByCategory(categoryName: String): Call<MealsByCategoryList>
}