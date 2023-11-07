package com.af.foodapp.ui.features.favorites_screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.af.foodapp.data.repository.FavoritesMealsRepository
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.databinding.FragmentFavoriteBinding
import com.af.foodapp.ui.adapters.FavoriteMealsAdapter
import com.af.foodapp.ui.features.MainActivity
import com.af.foodapp.ui.features.home_screen.HomeViewModel


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var favoriteMealsAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        favoriteMealsAdapter = FavoriteMealsAdapter()
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

    private fun setUpRecyclerView() {
        binding.rvFavorites.apply {
            adapter = favoriteMealsAdapter
            layoutManager = GridLayoutManager(context, 2, VERTICAL, false)
        }
    }

    private fun observerFavorites() {
        favoritesViewModel.observerFavoriteMealsLiveData()
            ?.observe(viewLifecycleOwner, Observer { favoriteMeals ->
                favoriteMealsAdapter.differ.submitList(favoriteMeals)
            })
    }

    private fun initViewModel() {
        val favoritesMealsRepository =
            FavoritesMealsRepository(
                localDataSource = MealDatabase.getInstance(requireContext().applicationContext).mealDao(),
            )
        val viewModelFactory = FavoritesViewModelFactory(favoritesMealsRepository)
        favoritesViewModel =
            ViewModelProvider(this, viewModelFactory)[FavoritesViewModel::class.java]
    }
//        favoritesViewModel = (activity as MainActivity).viewModel

}