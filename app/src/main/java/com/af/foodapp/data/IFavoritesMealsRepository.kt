package com.af.foodapp.data

import androidx.lifecycle.LiveData
import com.af.foodapp.data.source.local.model.Meal

interface IFavoritesMealsRepository {
    fun getFavoritesMeals(): LiveData<List<Meal>>
}