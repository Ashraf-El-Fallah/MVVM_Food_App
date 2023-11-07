package com.af.foodapp.data.repository

import com.af.foodapp.data.IFavoritesMealsRepository
import com.af.foodapp.data.source.local.MealDao

class FavoritesMealsRepository(
    private val localDataSource: MealDao
) : IFavoritesMealsRepository {
    override fun getFavoritesMeals() = localDataSource.getAllMeals()
}