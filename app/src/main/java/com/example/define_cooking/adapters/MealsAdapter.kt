package com.example.define_cooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.define_cooking.databinding.MealItemBinding
import com.example.define_cooking.pojo.Meal
import com.example.define_cooking.pojo.mealsBYcategory

class MealsAdapter:RecyclerView.Adapter<MealsAdapter.favmealsviewholder>() {
    lateinit var onItemsclicl:((mealsBYcategory)->Unit)
    private var mealList=ArrayList<mealsBYcategory>()
    fun setmeals(mealList: ArrayList<mealsBYcategory>){
        this.mealList=mealList
        notifyDataSetChanged()
    }
    inner class favmealsviewholder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)
    private val diffUtil=object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favmealsviewholder {
        return favmealsviewholder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )

    }

    override fun onBindViewHolder(holder: favmealsviewholder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.mealText.text=meal.strMeal

      /*  holder.itemView.setOnClickListener {
            onItemsclicl.invoke(mealList[position])
        }

       */


    }


    override fun getItemCount(): Int {
        return differ.currentList.size

    }
}