package com.kucharski.michal.weatheracc.di.modules

import com.kucharski.michal.weatheracc.repository.Repository
import com.kucharski.michal.weatheracc.repository.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(database: AppDatabase)  = Repository(database.weatherForecastDao())

}