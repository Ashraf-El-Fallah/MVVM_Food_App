package com.af.foodapp.data.repository

import com.af.foodapp.data.MealRepositoryInterface
import com.af.foodapp.data.source.local.model.MealList
import com.af.foodapp.data.source.remote.RetrofitInstance
import retrofit2.Call

class MealRepository(private val remoteDataSource: RetrofitInstance) : MealRepositoryInterface {

    //get everything about meal by passing id
    override fun getMealDetails(id: String): Call<MealList> =
        remoteDataSource.api.getMealDetails(id)
}
