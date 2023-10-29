package com.af.foodapp.ui.activites.meal_screen

import com.af.foodapp.model.MealList
import com.af.foodapp.retrofit.RetrofitInstance
import retrofit2.Call

class MealRepository {
    fun getMealDetails(id: String): Call<MealList> =
        RetrofitInstance.api.getMealDetails(id)
}
