package com.kucharski.michal.weatheracc.di.modules

import android.content.Context
import com.kucharski.michal.weatheracc.WeatherApplication
import dagger.Module
import dagger.Provides

@Module
class ContextModule {

    @Provides
    fun providesContext(application: WeatherApplication): Context = application.applicationContext
}