package com.af.foodapp.ui.fragments.home_screen

import com.af.foodapp.data.model.CategoryList
import com.af.foodapp.data.model.MealsByCategoryList
import com.af.foodapp.data.model.MealList
import com.af.foodapp.data.retrofit.RetrofitInstance
import retrofit2.Call

class HomeRepository {
    fun getRandomMeal(): Call<MealList> = RetrofitInstance.api.getRandomMeal()
    fun getPopularItems(): Call<MealsByCategoryList> =
        RetrofitInstance.api.getPopularItems("Seafood")
    fun getCategories(): Call<CategoryList> = RetrofitInstance.api.getCategories()
}