package com.af.foodapp.data.domain.repository

import com.af.foodapp.data.domain.CategoryMealsRepositoryInterface
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import retrofit2.Call

class CategoryMealsRepository(private val remoteDataSource: RetrofitInstance) :
    CategoryMealsRepositoryInterface {

    //get meals by choosing category you want
    override fun getMealsByCategory(categoryName: String): Call<MealsByCategoryList> =
        remoteDataSource.api.getMealsByCategory(categoryName)
}