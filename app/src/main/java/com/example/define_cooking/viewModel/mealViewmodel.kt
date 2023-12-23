package com.example.define_cooking.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.define_cooking.db.MealDataBase
import com.example.define_cooking.pojo.Meal
import com.example.define_cooking.pojo.MealList
import com.example.define_cooking.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class mealViewmodel(
    val mealDatabase:MealDataBase
):ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){

                    mealDetailsLiveData.value=response.body()!!.meals[0]



                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("home fragment",t.message.toString())
            }
        })
    }
    fun observeMEaldetailLIvedata(): LiveData<Meal> {
        return mealDetailsLiveData
    }


    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun deletetMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
}