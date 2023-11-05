package com.af.foodapp.ui.features.home_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af.foodapp.data.repository.HomeRepository
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

class HomeViewModel() : ViewModel() {
    //MutableLiveData means you can change it .. but live data you can't change it

    private val homeRepository: HomeRepository =
        HomeRepository(remoteDataSource = RetrofitInstance, localDataSource = MealDatabase)

    //live data to observe changes in home fragment
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealLiveData = homeRepository.getFavoritesMeals()

    //    private var remoteDataSource = RetrofitInstance

    //get response and pass the random meal to live data
    fun getRandomMeal() {
        homeRepository.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }
        })
    }

    //get response and pass the popular meals to live data
    fun getPopularItems() {
        homeRepository.getPopularItems()
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("Home Fragment", t.message.toString())
                }
            })
    }

    //get response and pass the categories to live data
    fun getCategories() {
        homeRepository.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {
                    categoriesLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("MainViewModel", t.message.toString())
            }
        })
    }

    //live data you can't change it so we use it here

    //three observers to update views in home fragment
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observerFavoriteMealsLiveData(): LiveData<List<Meal>> {
        return favoritesMealLiveData
    }
}