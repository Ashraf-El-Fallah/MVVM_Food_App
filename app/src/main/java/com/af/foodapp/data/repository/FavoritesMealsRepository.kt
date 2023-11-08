package com.af.foodapp.data.repository

import com.af.foodapp.data.IFavoritesMealsRepository
import com.af.foodapp.data.source.local.MealDao
import com.af.foodapp.data.source.local.model.Meal

class FavoritesMealsRepository(
    private val localDataSource: MealDao
) : IFavoritesMealsRepository {
    override fun getFavoritesMeals() = localDataSource.getAllMeals()
    override suspend fun deleteMeal(meal: Meal) = localDataSource.deleteMeal(meal)
}