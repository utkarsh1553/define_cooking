package com.example.define_cooking.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.ActivityId
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.define_cooking.R
import com.example.define_cooking.databinding.ActivityMealBinding
import com.example.define_cooking.db.MealDataBase
import com.example.define_cooking.fragments.homeFragment
import com.example.define_cooking.pojo.Meal
import com.example.define_cooking.viewModel.HomeViewModel
import com.example.define_cooking.viewModel.MealViewModelFactory
import com.example.define_cooking.viewModel.mealViewmodel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var utube: String
    private lateinit var mealMVVM:mealViewmodel

    private lateinit var binding: ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mealDataBase=MealDataBase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDataBase)
        mealMVVM= ViewModelProvider(this,viewModelFactory)[mealViewmodel::class.java]

       // mealMVVM= ViewModelProviders.of(this)[mealViewmodel::class.java]
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMalinfofromintent()
        setInfoinviews()
        loadingCAse()
        mealMVVM.getMealDetail(mealId)
        observeMealdataLIveDATA()
        onutubeclick()
        onfavcliclk()

    }

    private fun onfavcliclk() {
        binding.addtofav.setOnClickListener {
           mealToSave?.let {
               mealMVVM.insertMeal(it)
               Toast.makeText(this, "added to favorites", Toast.LENGTH_SHORT).show();
           }
        }
    }

    private fun onutubeclick() {
        binding.utube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(utube))
            startActivity(intent)
        }
    }
    private var mealToSave:Meal?=null

    private fun observeMealdataLIveDATA() {
        mealMVVM.observeMEaldetailLIvedata().observe(this,object : Observer<Meal>{
            @SuppressLint("SetTextI18n")
            override fun onChanged(value: Meal) {
                onresponse()
                val meal = value
                mealToSave = meal
                binding.tvCat.text="Category:${meal!!.strCategory}"
                binding.tvLoc.text=meal!!.strArea
                binding.tvText.text= meal!!.strInstructions
                utube = meal.strYoutube.toString()


            }
        })

    }

    private fun setInfoinviews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title= mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.red))
       binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))


    }

    private fun getMalinfofromintent() {
       val intent = intent
        mealId= intent.getStringExtra(homeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(homeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(homeFragment.MEAL_THUMB)!!



    }
    private fun loadingCAse(){
        binding.pb.visibility= View.VISIBLE
        binding.addtofav.visibility=View.INVISIBLE
        binding.tvText.visibility=View.INVISIBLE
        binding.tvCat.visibility=View.INVISIBLE
        binding.tvLoc.visibility=View.INVISIBLE
        binding.utube.visibility=View.INVISIBLE



    }

    private fun onresponse(){
        binding.pb.visibility= View.INVISIBLE
        binding.addtofav.visibility=View.VISIBLE
        binding.tvText.visibility=View.VISIBLE
        binding.tvCat.visibility=View.VISIBLE
        binding.tvLoc.visibility=View.VISIBLE
        binding.utube.visibility=View.VISIBLE


    }
}