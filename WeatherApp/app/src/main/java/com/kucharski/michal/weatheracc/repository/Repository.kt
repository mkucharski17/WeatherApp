package com.kucharski.michal.weatheracc.repository

import com.kucharski.michal.weatheracc.models.Units
import com.kucharski.michal.weatheracc.models.WeatherForecast
import com.kucharski.michal.weatheracc.repository.local.WeatherForecastDao
import com.kucharski.michal.weatheracc.repository.remote.OpenWeatherService

class Repository(
    private val openWeatherService: OpenWeatherService,
    private val weatherForecastDao: WeatherForecastDao
) {

    fun getWeatherListFlow() = weatherForecastDao.getAllFlow()

    suspend fun getWeatherList() = weatherForecastDao.getAll()

    suspend fun fetchWeatherByCityIdList(
        cityIdList: List<Long>,
        units: Units = Units.METRIC
    ) = openWeatherService.getWeatherByCityIdList(
            cityIdList.fold("", { acc: String, cityId: Long ->
                "$acc$cityId"
            }), units.name.toLowerCase()
        ).also { weatherForecastDao.insert(it.list) }

    suspend fun findCityByName(
        cityName: String,
        units: Units = Units.METRIC
    ) = openWeatherService.findCityWeatherByName(cityName, units.name.toLowerCase())

    suspend fun storeCity(weatherForecast: WeatherForecast) =
        weatherForecastDao.insert(weatherForecast)
}
