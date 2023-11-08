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
import com.af.foodapp.data.source.Result

class HomeRepository(
    private val remoteDataSource: MealApi,
) : IHomeRepository {
    override fun getRandomMeal(): LiveData<Result<Meal>> {
        val randomMealLiveData = MutableLiveData<Result<Meal>>()
        randomMealLiveData.value = Result.Loading

        remoteDataSource.getRandomMeal()
            .enqueue(object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response.isSuccessful) {
                        val randomMeal: Meal = response.body()!!.meals[0]
                        randomMealLiveData.value =
                            Result.Success(randomMeal)
                    } else {
                        randomMealLiveData.value =
                            Result.Error(Throwable(message = "Failed to fetch data"))
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("Home Fragment", t.message.toString())
                    randomMealLiveData.value = Result.Error(t)
                }
            })
        return randomMealLiveData
    }

    override fun getPopularItems(): LiveData<Result<List<MealsByCategory>>> {
        val popularItemsLiveData = MutableLiveData<Result<List<MealsByCategory>>>()
        popularItemsLiveData.value = Result.Loading

        remoteDataSource.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.isSuccessful) {
                        val popularMeals = response.body()!!.meals
                        popularItemsLiveData.value = Result.Success(popularMeals)
                    } else {
                        popularItemsLiveData.value =
                            Result.Error(Throwable(message = "Failed to fetch data"))
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("Home Fragment", t.message.toString())
                    popularItemsLiveData.value = Result.Error(t)

                }
            })
        return popularItemsLiveData
    }

    override fun getCategories(): LiveData<Result<List<Category>>> {
        val categoriesLiveData = MutableLiveData<Result<List<Category>>>()
        categoriesLiveData.value = Result.Loading

        remoteDataSource.getCategories()
            .enqueue(object : Callback<CategoryList> {
                override fun onResponse(
                    call: Call<CategoryList>,
                    response: Response<CategoryList>
                ) {
                    if (response.isSuccessful) {
                        categoriesLiveData.value = Result.Success(response.body()!!.categories)
                    } else {
                        categoriesLiveData.value =
                            Result.Error(Throwable(message = "Failed to fetch data"))
                    }
                }

                override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                    Log.d("MainViewModel", t.message.toString())
                    categoriesLiveData.value = Result.Error(t)
                }
            })
        return categoriesLiveData
    }

    override fun getMealById(id: String): LiveData<Result<Meal?>> {
        val mealLiveData = MutableLiveData<Result<Meal?>>()
        mealLiveData.value = Result.Loading

        remoteDataSource.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val meal = response.body()?.meals?.first()
                    mealLiveData.value = Result.Success(meal)
                } else {
                    mealLiveData.value = Result.Error(Throwable(message = "Failed to fetch data"))
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MainViewModel", t.message.toString())
                mealLiveData.value = Result.Error(t)
            }
        })
        return mealLiveData
    }

    override fun searchMeals(searchQuery: String): LiveData<Result<List<Meal>?>> {
        val searchedMealsLiveData = MutableLiveData<Result<List<Meal>?>>()
        searchedMealsLiveData.value = Result.Loading

        remoteDataSource.searchMeals(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    searchedMealsLiveData.value = Result.Success(meals)
                } else {
                    searchedMealsLiveData.value =
                        Result.Error(Throwable(message = "Failed to fetch data"))
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MainViewModel", t.message.toString())
                searchedMealsLiveData.value = Result.Error(t)
            }
        })
        return searchedMealsLiveData
    }

}