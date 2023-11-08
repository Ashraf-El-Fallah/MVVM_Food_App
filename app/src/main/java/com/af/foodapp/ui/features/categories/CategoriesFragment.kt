package com.af.foodapp.ui.features.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.af.foodapp.data.source.Result
import com.af.foodapp.data.source.remote.model.Category
import com.af.foodapp.databinding.FragmentCategoriesBinding
import com.af.foodapp.ui.features.MainActivity
import com.af.foodapp.ui.features.adapters.CategoriesListAdapter
import com.af.foodapp.ui.features.categorymeals.CategoryMealsActivity
import com.af.foodapp.ui.features.homescreen.HomeViewModel
import com.af.foodapp.util.MealConstants

class CategoriesFragment : Fragment() {
    lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesListAdapter
    private lateinit var categoriesViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoriesViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        onObserveCategories()
    }

    private fun onObserveCategories() {
        categoriesViewModel.getCategories().observe(viewLifecycleOwner) { categoriesList ->
            when (categoriesList) {
                Result.Loading -> binding.progressBar.isVisible = false

                is Result.Success -> {
                    categoriesAdapter.differ.submitList(categoriesList.data)
                }

                is Result.Error -> {
                    binding.progressBar.isVisible = true
                    Toast.makeText(
                        context,
                        categoriesList.throwable?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        categoriesAdapter = CategoriesListAdapter { clickedCategory ->
            navigateToCategoryMeals(clickedCategory)
        }
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun navigateToCategoryMeals(clickedCategory: Category) {
        val intent = Intent(activity, CategoryMealsActivity::class.java)
        intent.putExtra(MealConstants.CATEGORY_NAME, clickedCategory.strCategory)
        startActivity(intent)
    }


}