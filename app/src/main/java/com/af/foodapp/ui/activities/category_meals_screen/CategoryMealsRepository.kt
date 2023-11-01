package com.af.foodapp.ui.activities.category_meals_screen

import com.af.foodapp.data.retrofit.RetrofitInstance

class CategoryMealsRepository {

    //get meals by choosing category you want
    fun getMealsByCategory(categoryName: String) =
        RetrofitInstance.api.getMealsByCategory(categoryName)
}