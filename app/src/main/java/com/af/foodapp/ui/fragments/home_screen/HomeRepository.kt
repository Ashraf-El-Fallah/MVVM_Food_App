package com.af.foodapp.ui.fragments.home_screen

import com.af.foodapp.model.MealList
import com.af.foodapp.retrofit.RetrofitInstance
import retrofit2.Call

class HomeRepository {
    fun getRandomMeal(): Call<MealList> = RetrofitInstance.api.getRandomMeal()

}