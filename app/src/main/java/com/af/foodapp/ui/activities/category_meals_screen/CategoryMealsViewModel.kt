package com.af.foodapp.ui.activities.category_meals_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af.foodapp.data.model.MealsByCategory
import com.af.foodapp.data.model.MealsByCategoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {
    //use live data to update meals when click on category
    private val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    //check the response and update the meals or display message in log
    fun getMealsByCategory(categoryName: String) {
        CategoryMealsRepository().getMealsByCategory(categoryName)
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
    }

    //to observe live data in activity and use abstraction
    fun observeMealLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
}