package com.af.foodapp.ui.fragments.home_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af.foodapp.model.Meal
import com.af.foodapp.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    //MutableLiveData means you can change it .. but live data you can't change it
    private var randomMealLiveData = MutableLiveData<Meal>()
    fun getRandomMeal() {
        HomeRepository().getRandomMeal().enqueue(object : Callback<MealList> {
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

    //live data you can't change it so we use it here
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

}