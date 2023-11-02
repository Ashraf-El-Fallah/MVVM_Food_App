package com.af.foodapp.data.repository

import com.af.foodapp.data.source.remote.model.MealList
import com.af.foodapp.data.source.remote.RetrofitInstance
import retrofit2.Call

class MealRepository(private val remoteDataSource: RetrofitInstance) {
    //get everything about meal by passing id
    fun getMealDetails(id: String): Call<MealList> =
        remoteDataSource.api.getMealDetails(id)
}
