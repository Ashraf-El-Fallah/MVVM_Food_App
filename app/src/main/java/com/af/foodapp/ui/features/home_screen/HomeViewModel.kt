package com.af.foodapp.ui.features.home_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af.foodapp.data.IHomeRepository
import com.af.foodapp.data.repository.HomeRepository
import com.af.foodapp.data.source.local.MealDao
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.data.source.remote.model.CategoryList
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.local.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val homeRepository: IHomeRepository
) : ViewModel() {
    //MutableLiveData means you can change it .. but live data you can't change it

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal() {
        randomMealLiveData = homeRepository.getRandomMeal()
    }

    fun getPopularItems() {
        popularItemsLiveData = homeRepository.getPopularItems()
    }

    fun getCategories() {
        categoriesLiveData = homeRepository.getCategories()
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

}