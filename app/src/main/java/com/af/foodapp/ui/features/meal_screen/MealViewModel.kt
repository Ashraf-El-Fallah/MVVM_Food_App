package com.af.foodapp.ui.features.meal_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.data.source.remote.model.Meal
import com.af.foodapp.data.source.remote.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel() : ViewModel() {
    //use live data to update meals when click on category
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    private var mealRepository: MealRepository = MealRepository(remoteDataSource = RetrofitInstance)

    //get the meal response
    fun getMealDetail(id: String) {
        mealRepository.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }
        })
    }

    //to observe live data in activity and use abstraction
    fun observerMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }
}