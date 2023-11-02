package com.af.foodapp.ui.features.category_meals_screen

import com.af.foodapp.data.source.remote.RetrofitInstance

class CategoryMealsRepository(private val remoteDataSource : RetrofitInstance){

    //get meals by choosing category you want
    fun getMealsByCategory(categoryName: String) =
        remoteDataSource.api.getMealsByCategory(categoryName)
}