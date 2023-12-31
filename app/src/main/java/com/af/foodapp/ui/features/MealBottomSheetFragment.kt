package com.af.foodapp.ui.features

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.af.foodapp.data.source.Result
import com.af.foodapp.databinding.FragmentMealBottomSheetBinding
import com.af.foodapp.ui.features.homescreen.HomeViewModel
import com.af.foodapp.ui.features.mealscreen.MealActivity
import com.af.foodapp.util.MealConstants
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "mealId"

class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeBottomSheetMeal()
        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(MealConstants.MEAL_NAME, mealName)
                    putExtra(MealConstants.MEAL_ID, mealId)
                    putExtra(MealConstants.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName: String? = null
    private var mealThumb: String? = null
    private fun observeBottomSheetMeal() {
        mealId?.let {
            viewModel.getMealById(it).observe(viewLifecycleOwner) { meal ->
                when (meal) {
                    Result.Loading -> binding.progressBar.isVisible = true

                    is Result.Success -> {
                        binding.progressBar.isVisible = false
                        Glide.with(this).load(meal.data?.strMealThumb).into(binding.imgCategory)
                        binding.tvMealCountry.text = meal.data?.strArea
                        binding.tvMealCategory.text = meal.data?.strCategory
                        binding.tvMealNameInBtmsheet.text = "${meal.data?.strMeal?.take(25)}.."
                        mealName = meal.data?.strMeal
                        mealThumb = meal.data?.strMealThumb
                    }

                    is Result.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            context,
                            meal.throwable?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}