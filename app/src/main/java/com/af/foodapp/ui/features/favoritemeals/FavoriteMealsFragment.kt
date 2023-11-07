package com.af.foodapp.ui.features.favoritemeals

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.af.foodapp.data.repository.FavoritesMealsRepository
import com.af.foodapp.data.repository.MealRepository
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.databinding.FragmentFavoriteBinding
import com.af.foodapp.ui.features.mealscreen.MealActivity
import com.af.foodapp.util.MealConstants
import com.google.android.material.snackbar.Snackbar


class FavoriteMealsFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteMealsViewModel: FavoriteMealsViewModel
    private lateinit var favoriteMealsAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        favoriteMealsAdapter = FavoriteMealsAdapter { clickedMeal ->
            navigateToMealDetails(clickedMeal)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observerFavorites()
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.rvFavorites)
        }
    }

    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val currentMeal = favoriteMealsAdapter.differ.currentList[position]
            favoriteMealsViewModel.deleteMeal(currentMeal)

            Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction("Undo") {
                favoriteMealsViewModel.insertMeal(currentMeal)
            }.show()
        }
    }

    private fun navigateToMealDetails(clickedMeal: Meal) {
        val intent = Intent(activity, MealActivity::class.java)
        intent.putExtra(MealConstants.MEAL_NAME, clickedMeal.strMeal)
        intent.putExtra(MealConstants.MEAL_ID, clickedMeal.idMeal)
        intent.putExtra(MealConstants.MEAL_THUMB, clickedMeal.strMealThumb)
        startActivity(intent)
    }

    private fun setUpRecyclerView() {
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoriteMealsAdapter
        }
    }

    private fun observerFavorites() {
        favoriteMealsViewModel.observerFavoriteMealsLiveData()
            .observe(viewLifecycleOwner) { favoriteMeals ->
                favoriteMealsAdapter.differ.submitList(favoriteMeals)
            }
    }

    private fun initViewModel() {
        val favoritesMealsRepository =
            FavoritesMealsRepository(
                localDataSource = MealDatabase.getInstance(requireContext())
                    .mealDao(),
            )
        val mealRepository = MealRepository(
            localDataSource = MealDatabase.getInstance(requireContext().applicationContext)
                .mealDao(),
            remoteDataSource = RetrofitInstance.api
        )
        val viewModelFactory =
            FavoriteMealsViewModelFactory(favoritesMealsRepository, mealRepository)
        favoriteMealsViewModel =
            ViewModelProvider(this, viewModelFactory)[FavoriteMealsViewModel::class.java]
    }
}