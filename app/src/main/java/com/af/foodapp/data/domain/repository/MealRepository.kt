package com.af.foodapp.data.domain.repository

import com.af.foodapp.data.domain.MealRepositoryInterface
import com.af.foodapp.data.source.remote.model.MealList
import com.af.foodapp.data.source.remote.RetrofitInstance
import retrofit2.Call

class MealRepository(private val remoteDataSource: RetrofitInstance) : MealRepositoryInterface {

    //get everything about meal by passing id
    override fun getMealDetails(id: String): Call<MealList> =
        remoteDataSource.api.getMealDetails(id)
}
