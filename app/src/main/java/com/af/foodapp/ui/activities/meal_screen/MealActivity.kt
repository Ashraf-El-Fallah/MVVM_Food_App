package com.af.foodapp.ui.activities.meal_screen

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.databinding.ActivityMealBinding
import com.af.foodapp.util.MealConstants
import com.bumptech.glide.Glide

//this activity used to display the information of the meal like steps to cook it and link to open youtube
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
        loadingCase()
        setMealInformationInViews()
        initViewModel()
        observerMealDetailsLiveData()
        onYoutubeImageClick()
    }

    //to open video in youtube to show how to cook this meal
    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    //observe the information about this meal to update the ui information
    private fun observerMealDetailsLiveData() {
        mealViewModel.observerMealDetailsLiveData().observe(this, Observer {
            onResponseCase()
            binding.tvCategory.text = "Category : ${it.strCategory}"
            binding.tvArea.text = "Area : ${it.strArea}"
            binding.tvInstructionsSteps.text = it.strInstructions
            youtubeLink = it.strYoutube
        })
    }

    //initialization for view model and pass meal id to api to get the response
    private fun initViewModel() {
        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]
        mealViewModel.getMealDetail(mealId)
    }

    //set imageview and meal name
    private fun setMealInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName

        //this set color when collapsing and extended but we use it in xml files

//        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
//        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    //get some meal information from the home fragment
    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(MealConstants.MEAL_ID)!!
        mealName = intent.getStringExtra(MealConstants.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MealConstants.MEAL_THUMB)!!
    }

    //this is used when response comes from api
    //visible progress bar and hide other views
    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFavorites.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    //this is used after the response
    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFavorites.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }
}