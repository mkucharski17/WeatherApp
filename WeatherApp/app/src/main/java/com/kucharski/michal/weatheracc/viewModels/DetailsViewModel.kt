package com.kucharski.michal.weatheracc.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucharski.michal.weatheracc.models.WeatherForecast
import com.kucharski.michal.weatheracc.models.WeatherForecastFor5Days
import com.kucharski.michal.weatheracc.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    val hourlyWeatherForecast = MutableLiveData<WeatherForecastFor5Days>()

    init {
        viewModelScope.launch {
            try{
                val result = repository.fetchWeatherFor5DaysByCityId(524901L)
                hourlyWeatherForecast.postValue(result)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}
