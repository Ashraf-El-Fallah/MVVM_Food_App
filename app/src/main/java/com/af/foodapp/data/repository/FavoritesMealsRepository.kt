package com.af.foodapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.IFavoritesMealsRepository
import com.af.foodapp.data.source.local.MealDao
import com.af.foodapp.data.source.local.model.Meal

class FavoritesMealsRepository(
    private val localDataSource: MealDao?
) : IFavoritesMealsRepository {
    override fun getFavoritesMeals(): LiveData<List<Meal>>? = localDataSource?.getAllMeals()
}