package com.af.foodapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.IHomeRepository
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.model.CategoryList
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import com.af.foodapp.data.source.local.model.MealList
import com.af.foodapp.data.source.remote.MealApi
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.data.source.remote.model.MealsByCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository(
    private val remoteDataSource: MealApi,
) : IHomeRepository {
    override fun getRandomMeal(): LiveData<Meal> {
        val randomMealLiveData = MutableLiveData<Meal>()
        remoteDataSource.getRandomMeal()
            .enqueue(object : Callback<MealList> {
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
        return randomMealLiveData
    }

    override fun getPopularItems(): LiveData<List<MealsByCategory>> {
        val popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
        remoteDataSource.getPopularItems("Seafood")
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
        return popularItemsLiveData
    }

    override fun getCategories(): LiveData<List<Category>> {
        val categoriesLiveData = MutableLiveData<List<Category>>()
        remoteDataSource.getCategories()
            .enqueue(object : Callback<CategoryList> {
                override fun onResponse(
                    call: Call<CategoryList>,
                    response: Response<CategoryList>
                ) {
                    response.body()?.let {
                        categoriesLiveData.postValue(it.categories)
                    }
                }

                override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                    Log.d("MainViewModel", t.message.toString())
                }
            })
        return categoriesLiveData
    }

    override fun getMealById(id: String): LiveData<Meal?> {
        val mealLiveData = MutableLiveData<Meal?>()
        remoteDataSource.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.first()
                    mealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MainViewModel", t.message.toString())
            }
        })
        return mealLiveData
    }

    override fun searchMeals(searchQuery: String): LiveData<List<Meal>?> {
        val searchedMealsLiveData = MutableLiveData<List<Meal>?>()
        remoteDataSource.searchMeals(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    meals?.let {
                        searchedMealsLiveData.postValue(meals)
                    }
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MainViewModel", t.message.toString())
            }
        })
        return searchedMealsLiveData
    }

}