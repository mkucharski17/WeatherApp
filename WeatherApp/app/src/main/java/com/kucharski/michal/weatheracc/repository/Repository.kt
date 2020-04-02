package com.kucharski.michal.weatheracc.repository

import com.kucharski.michal.weatheracc.repository.local.WeatherForecastDao

class Repository(private val weatherForecastDao: WeatherForecastDao) {

    fun getWeatherList() = weatherForecastDao.getAll()
}
