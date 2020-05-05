package com.kucharski.michal.weatheracc.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucharski.michal.weatheracc.models.Units
import com.kucharski.michal.weatheracc.models.WeatherForecast
import com.kucharski.michal.weatheracc.repository.Repository
import com.kucharski.michal.weatheracc.repository.local.getUnits
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SearchCityViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val cityList = MutableLiveData<List<WeatherForecast>>()
    val errorMessage = MutableLiveData<String>()

    fun searchCity(cityName: String) {
        viewModelScope.launch {
            try {
                val result = repository.findCityByName(cityName,sharedPreferences.getUnits())
                cityList.postValue(result.list)
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.toString())
            }
        }
    }

    fun storeCity(weatherForecast: WeatherForecast) {
        viewModelScope.launch {
            repository.storeCity(weatherForecast)
        }
    }
}