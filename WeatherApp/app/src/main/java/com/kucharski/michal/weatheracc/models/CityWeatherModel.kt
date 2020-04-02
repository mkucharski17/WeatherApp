package com.kucharski.michal.weatheracc.models

data class CityWeatherModel (
    val id: String,
    val name: String,
    val date: String = "19.03.2020",
    val status: String,
    val temperature: Int
)