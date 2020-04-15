package com.kucharski.michal.weatheracc.repository.remote

import com.kucharski.michal.weatheracc.models.FindCityWeatherResponse
import com.kucharski.michal.weatheracc.models.WeatherCityListResponse
import com.kucharski.michal.weatheracc.models.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("data/2.5/weather?q=London&appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getWeatherByCityId(
        @Query("id") cityId: Long,
        @Query("units") units: String = "metric"
    ): WeatherForecast

    @GET("data/2.5/group?id=524901,703448,2643743&units=metric&appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun getWeatherByCityIdList(
        @Query("id") cityIdList: String,
        @Query("units") units: String = "metric"
    ): WeatherCityListResponse

    @GET("data/2.5/weather?q=London&appid=15646a06818f61f7b8d7823ca833e1ce")
    suspend fun findCityWeatherByName(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric"
    ): FindCityWeatherResponse

}