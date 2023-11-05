package com.af.foodapp.data

import androidx.lifecycle.LiveData
import com.af.foodapp.data.source.Result
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import retrofit2.Call

interface ICategoryMealsRepository {
    fun getMealsByCategory(categoryName: String): LiveData<Result<List<MealsByCategory>>>
}