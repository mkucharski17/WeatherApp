package com.kucharski.michal.weatheracc.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucharski.michal.weatheracc.models.WeatherForecast
import com.kucharski.michal.weatheracc.repository.Repository
import kotlinx.coroutines.flow.*

class ForecastListViewModel(repository: Repository) : ViewModel() {
    private val tag = "ForecastListVViewModel"

    val weatherList = MutableLiveData<List<WeatherForecast>>()

    init{
        repository.getWeatherList()
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

    }
}
