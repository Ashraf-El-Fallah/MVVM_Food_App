package com.af.foodapp.ui.features.home_screen

import com.af.foodapp.data.source.remote.model.CategoryList
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import com.af.foodapp.data.source.remote.model.MealList
import com.af.foodapp.data.source.remote.RetrofitInstance
import retrofit2.Call

class HomeRepository (
    private val remoteDataSource: RetrofitInstance
){
    fun getRandomMeal(): Call<MealList> = remoteDataSource.api.getRandomMeal()
    fun getPopularItems(): Call<MealsByCategoryList> =
        remoteDataSource.api.getPopularItems("Seafood")
    fun getCategories(): Call<CategoryList> = remoteDataSource.api.getCategories()
}