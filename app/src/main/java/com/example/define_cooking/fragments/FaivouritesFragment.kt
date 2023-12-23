package com.example.define_cooking.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.define_cooking.activity.MainActivity
import com.example.define_cooking.activity.MealActivity
import com.example.define_cooking.adapters.MealsAdapter
import com.example.define_cooking.databinding.FragmentFaivourites2Binding
import com.example.define_cooking.pojo.mealsBYcategory
import com.example.define_cooking.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [faivouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class faivouritesFragment : Fragment() {
    private lateinit var binding: FragmentFaivourites2Binding
    private lateinit var viewModel: HomeViewModel
    private lateinit var fAvMealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFaivourites2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparerec()
        observefav()
      //  onfavItemsClick()
        val itemTouchHelper= object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deletetMeal(fAvMealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(fAvMealsAdapter.differ.currentList[position])
                    }
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recFav)
    }


    private fun preparerec() {
        fAvMealsAdapter= MealsAdapter()
        binding.recFav.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = fAvMealsAdapter
        }
    }

    private fun observefav() {
        viewModel.observefavslivedata().observe(requireActivity(), Observer {
            meals->
            fAvMealsAdapter.differ.submitList(meals)
           // fAvMealsAdapter.differ.setmeals(mealList = mealList as ArrayList<mealsBYcategory>)
            fAvMealsAdapter.setmeals(mealList = meals as ArrayList<mealsBYcategory>)

        })
    }
    private fun onfavItemsClick() {
        fAvMealsAdapter.onItemsclicl={
                meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(homeFragment.MEAL_ID,meal.idMeal)
            intent.putExtra(homeFragment.MEAL_THUMB,meal.strMealThumb)
            intent.putExtra(homeFragment.MEAL_NAME,meal.strMeal)
            startActivity(intent)
        }
    }


}