package com.kucharski.michal.weatheracc.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kucharski.michal.weatheracc.repository.Repository


class ViewModelFactory (private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass){
            ForecastListViewModel::class.java -> {
                ForecastListViewModel(repository) as T
            }
            SplashViewModel::class.java -> {
                SplashViewModel(repository) as T
            }
            SearchCityViewModel::class.java -> {
                SplashViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }

        }
    }
}