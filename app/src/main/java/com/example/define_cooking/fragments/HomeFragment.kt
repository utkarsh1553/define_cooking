package com.example.define_cooking.fragments



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.define_cooking.R
import com.example.define_cooking.activity.MainActivity

import com.example.define_cooking.activity.MealActivity
import com.example.define_cooking.activity.categoriesmealactivity
import com.example.define_cooking.adapters.category_adapter
import com.example.define_cooking.adapters.popular_meal_adapter
import com.example.define_cooking.databinding.FragmentHome2Binding
import com.example.define_cooking.pojo.Meal
import com.example.define_cooking.pojo.mealsBYcategory
import com.example.define_cooking.viewModel.HomeViewModel
import kotlinx.coroutines.launch
import kotlin.concurrent.timer


class homeFragment : Fragment() {
    private lateinit var binding:FragmentHome2Binding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularitemsadapter: popular_meal_adapter
    private lateinit var categoriesAdapter: category_adapter

    companion object{
        const val MEAL_ID="com.example.define_cooking.fragments.idMeal"
        const val MEAL_NAME="com.example.define_cooking.fragments.nameMeal"
        const val MEAL_THUMB="com.example.define_cooking.fragments.thumbMeal"
        const val CATEGORY_NAME="com.example.define_cooking.fragments.categoryName"
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        popularitemsadapter= popular_meal_adapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentHome2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparpopularitemsrecyclerview()


        observerRandomMeal()

        onRandomMealClick()

        viewModel.getpopularitems()
        observePopularItemsLiveData()
        onpopularItemsClick()

        preparecategoryrecyclerview()
        viewModel.getcategories()
        observeCategoryLiveData()

        oncategoryclicl()

        onsrchitemcliclk()


    }

    private fun onsrchitemcliclk() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_searchFragment)
        }
    }


    private fun oncategoryclicl() {
        categoriesAdapter.onItemsclicl={
            category ->
            val intent = Intent(activity,categoriesmealactivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun preparecategoryrecyclerview(){
        categoriesAdapter= category_adapter()
        binding.recViewCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }

    }

    private fun observeCategoryLiveData() {
        viewModel.observecategorylivedata().observe(viewLifecycleOwner,Observer{
            categories->


                categoriesAdapter.setcategorylist(categories)



        })

    }

    private fun onpopularItemsClick() {
        popularitemsadapter.onItemsclicl={
            meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            startActivity(intent)
        }
    }


    private fun preparpopularitemsrecyclerview() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularitemsadapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observepopularMealLivedata().observe(viewLifecycleOwner
        ) { mealList->
            popularitemsadapter.setmeals(mealList = mealList as ArrayList<mealsBYcategory>)





        }
    }

    private fun onRandomMealClick(){
        binding.randomMealCard.setOnClickListener{


            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
       viewModel.observeRandomMealLivedata().observe(viewLifecycleOwner
       ) { meal ->
           Glide.with(this@homeFragment)
               .load(meal!!.strMealThumb)
               .into(binding.imgRandomMeal)
           this.randomMeal = meal
       }
    }


}