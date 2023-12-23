package com.example.define_cooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.define_cooking.databinding.CategoryItemBinding
import com.example.define_cooking.pojo.Category
import com.example.define_cooking.pojo.mealsBYcategory

class category_adapter():RecyclerView.Adapter<category_adapter.categorviewholder>() {
    private var categoryList=ArrayList<Category>()
     var onItemsclicl:((Category)->Unit)?=null
    fun setcategorylist(categoryList: List<Category>){
        this.categoryList=categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categorviewholder {
      return categorviewholder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: categorviewholder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.categoryTvName.text=categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemsclicl!!.invoke(categoryList[position])
        }

    }

    override fun getItemCount(): Int {
        return categoryList.size

    }

    inner class categorviewholder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)
}