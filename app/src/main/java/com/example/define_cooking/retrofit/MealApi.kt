package com.example.define_cooking.retrofit

import android.app.appsearch.exceptions.AppSearchException
import com.example.define_cooking.pojo.mealByCategoryList
import com.example.define_cooking.pojo.MealList
import com.example.define_cooking.pojo.CategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

@GET("random.php")
    fun getRandomMeal(): Call<MealList>


    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String):Call<MealList>
    @GET("filter.php")
    fun getpopularitenms(@Query("c")categoryName:String):Call<mealByCategoryList>

    @GET("categories.php")
    fun getCategory():Call<CategoryList>

    @GET("filter.php")
    fun getmealsbycategory(@Query("c")categoryName: String):Call<mealByCategoryList>


    @GET("search.php")
    fun searchMeal(@Query("s")searchQuery: String):Call<MealList>

}