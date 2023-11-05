package com.af.foodapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.IMealRepository
import com.af.foodapp.data.source.local.MealDao
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.local.model.MealList
import com.af.foodapp.data.source.remote.MealApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealRepository(
    private val localDataSource: MealDao?,
    private val remoteDataSource: MealApi
) : IMealRepository {
    override fun getMealDetails(id: String): MutableLiveData<Meal> {
        var mealDetailsLiveData = MutableLiveData<Meal>()
        remoteDataSource.getMealDetails(id)
            .enqueue(object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response.body() != null) {
                        mealDetailsLiveData.value = response.body()!!.meals[0]
                    } else return
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("MealActivity", t.message.toString())
                }
            })
        return mealDetailsLiveData
    }

    override suspend fun insertMeal(meal: Meal): Unit? =
        localDataSource?.upsertMeal(meal)

    override suspend fun deleteMeal(meal: Meal): Unit? =
        localDataSource?.deleteMeal(meal)
}
