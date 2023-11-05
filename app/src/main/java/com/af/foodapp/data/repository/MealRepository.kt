package com.af.foodapp.data.repository

import com.af.foodapp.data.IMealRepository
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.local.model.MealList
import com.af.foodapp.data.source.remote.RetrofitInstance
import retrofit2.Call

class MealRepository(
    private val localDataSource: MealDatabase.Companion,
    private val remoteDataSource: RetrofitInstance
) : IMealRepository {

    //get everything about meal by passing id
    override fun getMealDetails(id: String): Call<MealList> =
        remoteDataSource.api.getMealDetails(id)

    override suspend fun insertMeal(meal: Meal): Unit? =
        localDataSource.INSTANCE?.mealDao()?.upsertMeal(meal)

    override suspend fun deleteMeal(meal: Meal): Unit? =
        localDataSource.INSTANCE?.mealDao()?.deleteMeal(meal)
}
