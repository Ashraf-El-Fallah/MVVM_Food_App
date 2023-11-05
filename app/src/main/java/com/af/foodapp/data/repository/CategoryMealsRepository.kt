package com.af.foodapp.data.repository

import com.af.foodapp.data.ICategoryMealsRepository
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import retrofit2.Call

class CategoryMealsRepository(private val remoteDataSource: RetrofitInstance) :
    ICategoryMealsRepository {

    //get meals by choosing category you want
    override fun getMealsByCategory(categoryName: String): Call<MealsByCategoryList> =
        remoteDataSource.api.getMealsByCategory(categoryName)
}