package com.af.foodapp.ui.fragments.home_screen

import com.af.foodapp.data.model.CategoryList
import com.af.foodapp.data.model.MealList
import com.af.foodapp.data.retrofit.RetrofitInstance
import retrofit2.Call

class HomeRepository {
    fun getRandomMeal(): Call<MealList> = RetrofitInstance.api.getRandomMeal()
    fun getPopularItems(): Call<CategoryList> = RetrofitInstance.api.getPopularItems("Seafood")
}