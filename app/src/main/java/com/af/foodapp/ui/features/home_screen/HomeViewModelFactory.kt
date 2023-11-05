package com.af.foodapp.ui.features.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.af.foodapp.data.IHomeRepository
import com.af.foodapp.data.repository.HomeRepository

class HomeViewModelFactory(
    private val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }
}
