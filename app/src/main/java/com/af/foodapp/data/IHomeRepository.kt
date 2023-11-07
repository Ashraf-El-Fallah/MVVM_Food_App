package com.af.foodapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.data.source.remote.model.MealsByCategory

interface IHomeRepository {
    fun getRandomMeal(): MutableLiveData<Meal>
    fun getPopularItems(): MutableLiveData<List<MealsByCategory>>
    fun getCategories(): MutableLiveData<List<Category>>



    /////////////////////////////////////////////
//    fun getFavoritesMeals(): LiveData<List<Meal>>?



}