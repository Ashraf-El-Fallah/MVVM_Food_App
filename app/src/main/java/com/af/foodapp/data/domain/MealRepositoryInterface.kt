package com.af.foodapp.data.domain

import com.af.foodapp.data.source.remote.model.MealList
import retrofit2.Call

interface MealRepositoryInterface {
    fun getMealDetails(id: String): Call<MealList>
}