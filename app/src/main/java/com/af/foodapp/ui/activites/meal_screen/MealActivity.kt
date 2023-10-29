package com.af.foodapp.ui.activites.meal_screen

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

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealViewModel.observerMealDetailsLiveData().observe(this, Observer {
            onResponseCase()
            binding.tvCategory.text = "Category : ${it.strCategory}"
            binding.tvArea.text = "Area : ${it.strArea}"
            binding.tvInstructionsSteps.text = it.strInstructions
            youtubeLink = it.strYoutube
        })
    }

    private fun initViewModel() {
        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]
        mealViewModel.getMealDetail(mealId)
    }

    private fun setMealInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName

        //this set color when collapsing and extended but we use it in xml files

//        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
//        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(MealConstants.MEAL_ID)!!
        mealName = intent.getStringExtra(MealConstants.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MealConstants.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFavorites.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFavorites.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }
}