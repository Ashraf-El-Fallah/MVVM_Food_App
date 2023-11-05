package com.af.foodapp.data.repository

import androidx.lifecycle.LiveData
import com.af.foodapp.data.IHomeRepository
import com.af.foodapp.data.source.local.MealDao
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.model.CategoryList
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import com.af.foodapp.data.source.local.model.MealList
import com.af.foodapp.data.source.remote.MealApi
import com.af.foodapp.data.source.remote.RetrofitInstance
import retrofit2.Call

class HomeRepository(
    private val remoteDataSource: MealApi,
    private val localDataSource: MealDao
) : IHomeRepository {

    override fun getRandomMeal(): Call<MealList> = remoteDataSource.getRandomMeal()
    override fun getPopularItems(): Call<MealsByCategoryList> =
        remoteDataSource.getPopularItems("Seafood")

    override fun getCategories(): Call<CategoryList> = remoteDataSource.getCategories()

    override fun getFavoritesMeals(): LiveData<List<Meal>> = localDataSource.getAllMeals()
}