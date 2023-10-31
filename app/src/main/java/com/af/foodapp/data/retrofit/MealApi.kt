package com.af.foodapp.data.retrofit

import com.af.foodapp.data.model.CategoryList
import com.af.foodapp.data.model.MealsByCategoryList
import com.af.foodapp.data.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    //this endpoint come from url .. you know it after base url and when it have ? this means there is an id after it so don't take it
    //take from ? to the end of base url
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>
}