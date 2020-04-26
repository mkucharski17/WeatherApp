package com.kucharski.michal.weatheracc.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucharski.michal.weatheracc.getDayOfWeek
import com.kucharski.michal.weatheracc.getHourAndMinutes
import com.kucharski.michal.weatheracc.models.WeatherForecastFor5Days
import com.kucharski.michal.weatheracc.models.WeatherHourForecast
import com.kucharski.michal.weatheracc.repository.Repository
import com.kucharski.michal.weatheracc.repository.local.getUnits
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    val hourlyWeatherForecast = MutableLiveData<WeatherForecastFor5Days>()
    val dayHourlyForecast = MutableLiveData<List<WeatherHourForecast>>()
    val dailyList = MutableLiveData<List<WeatherHourForecast>>()
    val detailsList = MutableLiveData<List<Pair<String, String>>>()

    fun searchCity(cityId: Long) {
        viewModelScope.launch {
            try {
                val result =
                    repository.fetchWeatherFor5DaysByCityId(cityId, sharedPreferences.getUnits())
                generateDailyList(result)
                hourlyWeatherForecast.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateDayHourlyForecast(dayOfWeek: String) =
        dayHourlyForecast.postValue(hourlyWeatherForecast.value?.list?.filter { getDayOfWeek(it.dt) == dayOfWeek })


    private fun generateDailyList(weatherFor5Days: WeatherForecastFor5Days) {
        weatherFor5Days.list.let {

            val daily = mutableListOf<WeatherHourForecast>()
            var maxDayTempForecast = it[0]
            for (i in 0..it.lastIndex) {
                val day = getDayOfWeek(it[i].dt)

                if (day == getDayOfWeek(maxDayTempForecast.dt)) {
                    if (it[i].main.temp > maxDayTempForecast.main.temp)
                        maxDayTempForecast = it[i]
                } else {
                    daily.add(maxDayTempForecast)
                    maxDayTempForecast = it[i]
                }
            }
            dailyList.postValue(daily)
        }
    }

    fun findMinTemp(dayOfWeek: String) =
        hourlyWeatherForecast.value?.list?.filter { getDayOfWeek(it.dt) == dayOfWeek }
            ?.map { it.main.temp }?.min()?.toInt()


    fun findMaxTemp(dayOfWeek: String) =
        hourlyWeatherForecast.value?.list?.filter { getDayOfWeek(it.dt) == dayOfWeek }
            ?.map { it.main.temp }?.max()?.toInt()


    fun updateDetailList(weatherHourForecast: WeatherHourForecast) {
        val details = mutableListOf<Pair<String, String>>()
        details.add(
            Pair(
                "Feels like",
                weatherHourForecast.main.feels_like.toInt().toString() + "°"
            )
        )
        details.add(Pair("Humidity", weatherHourForecast.main.humidity.toString() + "%"))
        details.add(Pair("Wind", weatherHourForecast.wind.speed.toString() + " m/s"))
        details.add(
            Pair(
                "Sunrise",
                hourlyWeatherForecast.value?.city?.sunrise?.let { getHourAndMinutes(it) }) as Pair<String, String>
        )
        details.add(
            Pair(
                "Sunset",
                hourlyWeatherForecast.value?.city?.sunset?.let { getHourAndMinutes(it) }) as Pair<String, String>
        )
        detailsList.postValue(details)
    }
}
