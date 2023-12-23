package com.example.define_cooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.define_cooking.databinding.PopularItemsBinding
import com.example.define_cooking.pojo.mealsBYcategory

class popular_meal_adapter():RecyclerView.Adapter<popular_meal_adapter.popularMEalviewHOlder>() {
    lateinit var onItemsclicl:((mealsBYcategory)->Unit)
    private var mealList=ArrayList<mealsBYcategory>()
    fun setmeals(mealList: ArrayList<mealsBYcategory>){
        this.mealList=mealList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): popularMEalviewHOlder {
        return popularMEalviewHOlder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: popularMEalviewHOlder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemsclicl.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
       return mealList.size
    }
    class popularMEalviewHOlder( val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)
}