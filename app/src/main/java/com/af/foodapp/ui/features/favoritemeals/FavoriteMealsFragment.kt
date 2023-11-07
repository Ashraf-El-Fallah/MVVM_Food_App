package com.af.foodapp.ui.features.favoritemeals

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.af.foodapp.data.repository.FavoritesMealsRepository
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.databinding.FragmentFavoriteBinding
import com.af.foodapp.ui.features.mealscreen.MealActivity
import com.af.foodapp.util.MealConstants


class FavoriteMealsFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteMealsViewModel: FavoriteMealsViewModel
    private lateinit var favoriteMealsAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        favoriteMealsAdapter = FavoriteMealsAdapter {clickedMeal->
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
            adapter = favoriteMealsAdapter
            layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
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
        val viewModelFactory = FavoriteMealsViewModelFactory(favoritesMealsRepository)
        favoriteMealsViewModel =
            ViewModelProvider(this, viewModelFactory)[FavoriteMealsViewModel::class.java]
    }
}