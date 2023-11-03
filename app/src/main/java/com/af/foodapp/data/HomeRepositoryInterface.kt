package com.af.foodapp.data

import com.af.foodapp.data.source.remote.model.CategoryList
import com.af.foodapp.data.source.remote.model.MealList
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import retrofit2.Call

interface HomeRepositoryInterface {
    fun getRandomMeal(): Call<MealList>
    fun getPopularItems(): Call<MealsByCategoryList>
    fun getCategories(): Call<CategoryList>
}