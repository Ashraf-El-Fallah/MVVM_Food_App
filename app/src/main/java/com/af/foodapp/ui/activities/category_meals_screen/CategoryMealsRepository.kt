package com.af.foodapp.ui.activities.category_meals_screen

import com.af.foodapp.data.source.remote.RetrofitInstance

class CategoryMealsRepository {

    //get meals by choosing category you want
    fun getMealsByCategory(categoryName: String) =
        RetrofitInstance.api.getMealsByCategory(categoryName)
}