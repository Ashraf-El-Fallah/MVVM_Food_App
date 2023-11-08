package com.af.foodapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.IMealRepository
import com.af.foodapp.data.source.local.MealDao
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.local.model.MealList
import com.af.foodapp.data.source.remote.MealApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.af.foodapp.data.source.Result

class MealRepository(
    private val localDataSource: MealDao,
    private val remoteDataSource: MealApi
) : IMealRepository {

    override fun getMealDetails(id: String): LiveData<Result<Meal>> {
        val mealDetailsLiveData = MutableLiveData<Result<Meal>>()
        mealDetailsLiveData.value = Result.Loading

        remoteDataSource.getMealDetails(id)
            .enqueue(object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response.isSuccessful) {
                        mealDetailsLiveData.value = Result.Success(response.body()!!.meals[0])
                    } else {
                        mealDetailsLiveData.value =
                            Result.Error(Throwable(message = "Failed to fetch data"))
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("MealActivity", t.message.toString())
                    mealDetailsLiveData.value = Result.Error(t)
                }
            })
        return mealDetailsLiveData
    }

    override suspend fun upsertMeal(meal: Meal) = localDataSource.upsertMeal(meal)

}
