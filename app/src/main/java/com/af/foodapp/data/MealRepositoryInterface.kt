package com.af.foodapp.data

import com.af.foodapp.data.source.local.model.MealList
import retrofit2.Call

interface MealRepositoryInterface {
    fun getMealDetails(id: String): Call<MealList>
}