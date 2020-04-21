package com.kucharski.michal.weatheracc.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucharski.michal.weatheracc.models.WeatherForecast
import com.kucharski.michal.weatheracc.models.WeatherForecastFor5Days
import com.kucharski.michal.weatheracc.models.WeatherHourForecast
import com.kucharski.michal.weatheracc.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    val hourlyWeatherForecast = MutableLiveData<WeatherForecastFor5Days>()
    val dayHourlyForecast =  MutableLiveData<List<WeatherHourForecast>>()
    val dailyList = MutableLiveData<List<WeatherHourForecast>>()

    fun searchCity(cityId: Long) {
        viewModelScope.launch {
            try {
                val result = repository.fetchWeatherFor5DaysByCityId(cityId)
                generateDailyList(result)
                dayHourlyForecast.postValue(result.list.take(5))
                hourlyWeatherForecast.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun generateDailyList(weatherFor5DaysList: WeatherForecastFor5Days){
        weatherFor5DaysList.list.let {

            val daily = mutableListOf<WeatherHourForecast>()
            var maxDayTempForecast = it[0]
            for(i in 0..it.lastIndex){
                var day = getDay(it[i].dt)

                if (day == getDay(maxDayTempForecast.dt)) {
                    if (it[i].main.temp > maxDayTempForecast.main.temp)
                        maxDayTempForecast = it[i]
                }else {
                    daily.add(maxDayTempForecast)
                    maxDayTempForecast = it[i]
                }
            }
            dailyList.postValue(daily)
        }

    }

    private fun getDay(timeStamp: Int):String  = SimpleDateFormat("EEE").format(Date(timeStamp.toLong()*1000))
}
