package com.kucharski.michal.weatheracc.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucharski.michal.weatheracc.models.Units
import com.kucharski.michal.weatheracc.models.WeatherForecast
import com.kucharski.michal.weatheracc.repository.Repository
import com.kucharski.michal.weatheracc.repository.local.getUnits
import com.kucharski.michal.weatheracc.repository.local.setUnits
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ForecastListViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val tag = "ForecastListViewModel"

    val weatherList = MutableLiveData<List<WeatherForecast>>()
    val units = MutableLiveData<Units>()

    init {
        repository.getWeatherListFlow()
            .onStart {
                Log.d(tag, "Flow starting")
            }
            .onCompletion {
                Log.d(tag, "Flow complete")
            }
            .catch {
                Log.d(tag, "Flow error $it")
            }
            .onEach {
                Log.d(tag, "Flow success $it")
                weatherList.value = it
            }
            .launchIn(viewModelScope)
        units.value = sharedPreferences.getUnits()
    }


    fun updateUnits() {
        val currentUnits = sharedPreferences.getUnits()
        val newUnits = if (currentUnits == Units.METRIC) Units.IMPERIAL
        else Units.METRIC
        viewModelScope.launch {
            repository.getWeatherList().map { it.id }.let {
                try {
                    repository.fetchWeatherByCityIdList(it, newUnits)
                    sharedPreferences.setUnits(newUnits)
                    units.postValue(newUnits)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun removeCity(weatherForecast: WeatherForecast){
        val list = weatherList.value?.toMutableList()
        list?.remove(weatherForecast)
        weatherList.postValue(list)

        viewModelScope.launch {
            repository.removeCity(weatherForecast)
        }
    }
}