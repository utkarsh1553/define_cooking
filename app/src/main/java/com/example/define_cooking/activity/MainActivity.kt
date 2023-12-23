package com.example.define_cooking.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.define_cooking.R
import com.example.define_cooking.db.MealDataBase
import com.example.define_cooking.viewModel.HomeViewModel
import com.example.define_cooking.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
   val viewModel: HomeViewModel by lazy {
       val mealDataBase=MealDataBase.getInstance(this)
       val homeViewModelProviderFactory= HomeViewModelFactory(mealDataBase)
       ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
   }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      val bottomNavigation = findViewById<BottomNavigationView>(R.id.btn_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)









    }
}