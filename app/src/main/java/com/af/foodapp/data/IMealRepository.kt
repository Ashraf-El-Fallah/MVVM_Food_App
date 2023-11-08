package com.af.foodapp.data

import androidx.lifecycle.LiveData
import com.af.foodapp.data.source.Result
import com.af.foodapp.data.source.local.model.Meal

interface IMealRepository {
    fun getMealDetails(id: String): LiveData<Result<Meal>>
    suspend fun upsertMeal(meal: Meal)

}