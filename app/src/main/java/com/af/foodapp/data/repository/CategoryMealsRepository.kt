package com.af.foodapp.data.repository

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.ICategoryMealsRepository
import com.af.foodapp.data.source.Result
import com.af.foodapp.data.source.remote.MealApi
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsRepository(private val remoteDataSource: MealApi) :
    ICategoryMealsRepository {

    //get meals by choosing category you want
    override fun getMealsByCategory(categoryName: String): LiveData<Result<List<MealsByCategory>>> {
        val mealsLiveData = MutableLiveData<Result<List<MealsByCategory>>>()
        mealsLiveData.value = Result.Loading

        remoteDataSource.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.isSuccessful)
                        mealsLiveData.value = Result.Success(response.body()?.meals ?: emptyList())
                    else
                        mealsLiveData.value =
                            Result.Error(Throwable(message = "Failed to fetch data"))

                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("CategoryMealsViewModel", t.message.toString())
                    mealsLiveData.value = Result.Error(t)
                }
            })

        return mealsLiveData
    }
}