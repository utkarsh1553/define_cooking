package com.example.define_cooking.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.example.define_cooking.R
import com.example.define_cooking.activity.MainActivity
import com.example.define_cooking.activity.categoriesmealactivity
import com.example.define_cooking.adapters.category_adapter
import com.example.define_cooking.databinding.FragmentCategoryBinding
import com.example.define_cooking.databinding.FragmentFaivourites2Binding
import com.example.define_cooking.viewModel.HomeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [categoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class categoryFragment : Fragment() {
    private lateinit var binding:FragmentCategoryBinding
    private lateinit var categoriesAdapter: category_adapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getcategories()
        preparecategoryrecyclerview()
        observeCategoryLiveData()
        oncategoryclicl()
    }

    private fun preparecategoryrecyclerview(){
        categoriesAdapter= category_adapter()
        binding.recCatcategory.apply {
            layoutManager= GridLayoutManager(context,3, GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }

    }

    private fun observeCategoryLiveData() {
        viewModel.observecategorylivedata().observe(viewLifecycleOwner, Observer{
                categories->


            categoriesAdapter.setcategorylist(categories)



        })

    }
    private fun oncategoryclicl() {
        categoriesAdapter.onItemsclicl={
                category ->
            val intent = Intent(activity, categoriesmealactivity::class.java)
            intent.putExtra(homeFragment.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }



}