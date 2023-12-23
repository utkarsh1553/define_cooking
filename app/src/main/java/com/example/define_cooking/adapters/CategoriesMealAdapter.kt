package com.example.define_cooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.define_cooking.databinding.MealItemBinding
import com.example.define_cooking.pojo.mealsBYcategory

class CategoriesMealAdapter:RecyclerView.Adapter<CategoriesMealAdapter.CategoryMealsViewModel>() {
   private var mealsList = ArrayList<mealsBYcategory>()
    fun setMealsList(mealsList:List<mealsBYcategory>){
        this.mealsList = mealsList as ArrayList<mealsBYcategory>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
       return CategoryMealsViewModel(
           MealItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.mealText.text = mealsList[position].strMeal

    }

    override fun getItemCount(): Int {
     return   mealsList.size
    }

    inner class CategoryMealsViewModel(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)
}