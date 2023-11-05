package com.af.foodapp.data.repository

import androidx.lifecycle.LiveData
import com.af.foodapp.data.IHomeRepository
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.model.CategoryList
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import com.af.foodapp.data.source.local.model.MealList
import com.af.foodapp.data.source.remote.RetrofitInstance
import retrofit2.Call

class HomeRepository(
    private val remoteDataSource: RetrofitInstance,
    private val localDataSource: MealDatabase.Companion
) : IHomeRepository {

    override fun getRandomMeal(): Call<MealList> = remoteDataSource.api.getRandomMeal()
    override fun getPopularItems(): Call<MealsByCategoryList> =
        remoteDataSource.api.getPopularItems("Seafood")

    override fun getCategories(): Call<CategoryList> = remoteDataSource.api.getCategories()

    override fun getFavoritesMeals(): LiveData<List<Meal>> = localDataSource.INSTANCE!!.mealDao().getAllMeals()
}