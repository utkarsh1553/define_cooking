package com.example.define_cooking.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.define_cooking.db.MealDataBase

class MealViewModelFactory(
   private val mealDataBase: MealDataBase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return mealViewmodel(mealDataBase)as T
    }

}