package com.example.define_cooking.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.define_cooking.db.MealDataBase
import com.example.define_cooking.pojo.Category
import com.example.define_cooking.pojo.mealByCategoryList

import com.example.define_cooking.pojo.Meal
import com.example.define_cooking.pojo.MealList
import com.example.define_cooking.pojo.CategoryList
import com.example.define_cooking.pojo.mealsBYcategory
import com.example.define_cooking.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query
import kotlin.concurrent.timer

class HomeViewModel(
    private val mealDataBase: MealDataBase
):ViewModel() {
    private val updateInterval = 20000L
    private  var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData=MutableLiveData<List<mealsBYcategory>>()
    private var categorylivedata=MutableLiveData<List<Category>>()
    private var favoritemeallivedata = mealDataBase.mealDao().getAllMeals()
    private var searchedMealsLiveData=MutableLiveData<List<Meal>>()

    private var saveStateRandomMeal : Meal?=null

init {
    getRandomMeal()
}
    fun getRandomMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(object: Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal
                  //  saveStateRandomMeal = randomMeal

                   timer(initialDelay = 3000L, period = 10000000L ) {


                       getRandomMeal()
                   }








                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("home fragment",t.message.toString())
            }

        })
    }

    fun getcategories(){
        RetrofitInstance.api.getCategory().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               response.body()?.let { categoryList ->
                   categorylivedata.postValue(categoryList.categories)
               }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("home view-model",t.message.toString())
            }

        })
    }


    fun getpopularitems(){
        RetrofitInstance.api.getpopularitenms("Seafood").enqueue(object:Callback<mealByCategoryList>{
            override fun onResponse(call: Call<mealByCategoryList>, response: Response<mealByCategoryList>) {
                if (response.body() != null) {
                    popularItemsLiveData.value = response.body()!!.meals


                } else {
                    return
                }
            }

            override fun onFailure(call: Call<mealByCategoryList>, t: Throwable) {
                Log.d("home fragment",t.message.toString())
            }
        })
    }
    fun deletetMeal(meal:Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().upsert(meal)
        }
    }
    fun searchmeal(searchQuery: String){
        RetrofitInstance.api.searchMeal(searchQuery).enqueue(
            object : Callback<MealList>{
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    val mealList = response.body()?.meals
                    mealList?.let {
                        searchedMealsLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("home fragment",t.message.toString())
                }
            }
        )
    }
    fun observeSearchdMealLiveData():LiveData<List<Meal>> = searchedMealsLiveData

    fun observeRandomMealLivedata():LiveData<Meal>{
        return randomMealLiveData
    }


    fun observepopularMealLivedata():LiveData<List<mealsBYcategory>>{
        return popularItemsLiveData
    }

    fun observecategorylivedata():LiveData<List<Category>>{
        return categorylivedata

    }

    fun observefavslivedata():LiveData<List<Meal>>{
        return favoritemeallivedata
    }
}