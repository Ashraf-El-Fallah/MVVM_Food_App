package com.af.foodapp.ui.features.mealscreen

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.data.repository.MealRepository
import com.af.foodapp.data.source.Result
import com.af.foodapp.data.source.local.MealDatabase
import com.af.foodapp.data.source.local.model.Meal
import com.af.foodapp.data.source.remote.RetrofitInstance
import com.af.foodapp.databinding.ActivityMealBinding
import com.af.foodapp.util.MealConstants
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getMealInformationFromIntent()
//        loadingCase()
        setMealInformationInViews()
        initViewModel()
        observerMealDetailsLiveData()
        onYoutubeImageClick()
        onFavoriteMealClick()
    }

    private fun onFavoriteMealClick() {
        binding.btnAddToFavorites.setOnClickListener {
            mealToSave?.let {
                mealViewModel.insertMeal(it)
                Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave: Meal? = null

    private fun observerMealDetailsLiveData() {
        mealViewModel.getMealDetail(mealId).observe(this) { mealDetails ->
            when (mealDetails) {
                Result.Loading -> loadingCase()

                is Result.Success -> {
                    onResponseCase()
                    mealToSave = mealDetails.data
                    binding.tvCategory.text = "Category : ${mealDetails.data.strCategory}"
                    binding.tvArea.text = "Area : ${mealDetails.data.strArea}"
                    binding.tvInstructionsSteps.text = mealDetails.data.strInstructions
                    youtubeLink = mealDetails.data.strYoutube!!

                }

                is Result.Error -> {
                    onResponseCase()
                    Toast.makeText(
                        this,
                        mealDetails.throwable?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun initViewModel() {
        val mealRepository =
            MealRepository(
                localDataSource = MealDatabase.getInstance(applicationContext).mealDao(),
                remoteDataSource = RetrofitInstance.api
            )
        val viewModelFactory = MealViewModelFactory(mealRepository)
        mealViewModel = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
    }

    private fun setMealInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
    }

    private fun getMealInformationFromIntent() {
        mealId = intent.getStringExtra(MealConstants.MEAL_ID)!!
        mealName = intent.getStringExtra(MealConstants.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MealConstants.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}