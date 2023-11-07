package com.af.foodapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.af.foodapp.data.ICategoryMealsRepository
import com.af.foodapp.data.source.remote.MealApi
import com.af.foodapp.data.source.remote.model.MealsByCategory
import com.af.foodapp.data.source.remote.model.MealsByCategoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsRepository(private val remoteDataSource: MealApi) :
    ICategoryMealsRepository {

    //get meals by choosing category you want
    override fun getMealsByCategory(categoryName: String): LiveData<List<MealsByCategory>> {
        val mealsLiveData = MutableLiveData<List<MealsByCategory>>()
        remoteDataSource.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    response.body()?.let {
                        mealsLiveData.postValue(it.meals)
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("CategoryMealsViewModel", t.message.toString())
                }
            })
        return mealsLiveData
    }
}