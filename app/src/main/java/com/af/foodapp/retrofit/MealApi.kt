package com.af.foodapp.retrofit

import com.af.foodapp.model.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}