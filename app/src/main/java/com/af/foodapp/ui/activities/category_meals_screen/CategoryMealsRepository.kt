package com.af.foodapp.ui.activities.category_meals_screen

import com.af.foodapp.data.retrofit.RetrofitInstance

class CategoryMealsRepository {

    fun getMealsByCategory(categoryName: String) =
        RetrofitInstance.api.getMealsByCategory(categoryName)
}