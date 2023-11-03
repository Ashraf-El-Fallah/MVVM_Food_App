package com.af.foodapp.data.repository

import com.af.foodapp.data.HomeRepositoryInterface
import com.af.foodapp.data.source.remote.model.CategoryList
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import com.af.foodapp.data.source.remote.model.MealList
import com.af.foodapp.data.source.remote.RetrofitInstance
import retrofit2.Call

class HomeRepository(private val remoteDataSource: RetrofitInstance) : HomeRepositoryInterface {

    override fun getRandomMeal(): Call<MealList> = remoteDataSource.api.getRandomMeal()
    override fun getPopularItems(): Call<MealsByCategoryList> =
        remoteDataSource.api.getPopularItems("Seafood")
    override fun getCategories(): Call<CategoryList> = remoteDataSource.api.getCategories()
}