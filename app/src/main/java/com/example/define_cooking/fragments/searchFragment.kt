package com.example.define_cooking.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.define_cooking.R
import com.example.define_cooking.activity.MainActivity
import com.example.define_cooking.adapters.MealsAdapter
import com.example.define_cooking.databinding.FragmentCategoryBinding
import com.example.define_cooking.databinding.FragmentSearchBinding
import com.example.define_cooking.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [searchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class searchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchrecadapter:MealsAdapter

    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSearchBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparerecview()
        binding.imgSrch.setOnClickListener {
            srchmeals()
        }
        observeSearchmeals()

        var searchJob:Job?=null
        binding.edSrchBar.addTextChangedListener{
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchmeal(it.toString())
            }

        }


    }

    private fun observeSearchmeals() {
        viewModel.observeSearchdMealLiveData().observe(viewLifecycleOwner, Observer {
            mealsList->
            searchrecadapter.differ.submitList(mealsList)
        })
    }

    private fun srchmeals() {
        val searchQuery = binding.edSrchBar.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchmeal(searchQuery)
        }
    }

    private fun preparerecview() {
        searchrecadapter = MealsAdapter()
        binding .rvSrch.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchrecadapter
        }
    }


}