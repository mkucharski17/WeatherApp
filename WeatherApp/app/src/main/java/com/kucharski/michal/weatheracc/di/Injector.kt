package com.kucharski.michal.weatheracc.di

import android.content.Context
import com.kucharski.michal.weatheracc.repository.Repository
import com.kucharski.michal.weatheracc.repository.local.AppDatabase
import com.kucharski.michal.weatheracc.repository.local.WeatherForecastDao
import com.kucharski.michal.weatheracc.viewModels.ViewModelFactory

object Injector {
    private lateinit var appDatabase: AppDatabase
    private lateinit var repository: Repository
    private lateinit var factory: ViewModelFactory

    fun provideFactory(context: Context) =
        if (::factory.isInitialized) factory
        else ViewModelFactory(provideRepository(context)).also { factory = it }

    private fun provideRepository(context: Context) =
        if (::repository.isInitialized) repository
        else Repository(provideWeatherDao(context)).also { repository = it }

    private fun provideWeatherDao(context: Context): WeatherForecastDao =
        if (::appDatabase.isInitialized) appDatabase.weatherForecastDao()
        else AppDatabase.getInstance(context).let {
            appDatabase = it
            it.weatherForecastDao()
        }
}
