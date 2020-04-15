package com.kucharski.michal.weatheracc.di.modules

import com.kucharski.michal.weatheracc.repository.Repository
import com.kucharski.michal.weatheracc.repository.local.AppDatabase
import com.kucharski.michal.weatheracc.repository.remote.OpenWeatherService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        openWeatherService: OpenWeatherService,
        database: AppDatabase)  = Repository(openWeatherService,database.weatherForecastDao())

}