package com.example.define_cooking.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.define_cooking.R
import com.example.define_cooking.adapters.CategoriesMealAdapter
import com.example.define_cooking.databinding.ActivityCategoriesmealactivityBinding
import com.example.define_cooking.fragments.homeFragment
import com.example.define_cooking.viewModel.CategoryMealsViewModel

class categoriesmealactivity : AppCompatActivity() {
    lateinit var binding : ActivityCategoriesmealactivityBinding
    lateinit var categorymealviewmodel:CategoryMealsViewModel
    lateinit var categoryMealsadapter:CategoriesMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesmealactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preparerecview()

        categorymealviewmodel= ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
        categorymealviewmodel.getMealsBYCategory(intent.getStringExtra(homeFragment.CATEGORY_NAME)!!)


        categorymealviewmodel.observemealslivedata().observe(this, Observer {
            mealList->
            binding.tvCategoryCount.text=mealList.size.toString()
            categoryMealsadapter.setMealsList(mealList)

        })
    }

    private fun preparerecview() {
categoryMealsadapter= CategoriesMealAdapter()
binding.rvMeals.apply {
    layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
    adapter = categoryMealsadapter
}
    }
}