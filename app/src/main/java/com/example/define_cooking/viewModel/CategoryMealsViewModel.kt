package com.example.define_cooking.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.define_cooking.pojo.Category
import com.example.define_cooking.pojo.mealByCategoryList
import com.example.define_cooking.pojo.mealsBYcategory
import com.example.define_cooking.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {
    val mealsLiveData = MutableLiveData<List<mealsBYcategory>>()
    fun getMealsBYCategory(categoryName:String){
        RetrofitInstance.api.getmealsbycategory(categoryName).enqueue(object :Callback<mealByCategoryList>{
            override fun onResponse(
                call: Call<mealByCategoryList>,
                response: Response<mealByCategoryList>
            ) {
                response.body()?.let { mealsList->
                    mealsLiveData.postValue(mealsList.meals)
                }

            }

            override fun onFailure(call: Call<mealByCategoryList>, t: Throwable) {
                Log.d("categoryviewmodel",t.message.toString())

            }
        })

    }
    fun observemealslivedata():LiveData<List<mealsBYcategory>>{
        return mealsLiveData

    }
}