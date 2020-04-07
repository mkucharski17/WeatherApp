package com.kucharski.michal.weatheracc.di.components

import com.kucharski.michal.weatheracc.WeatherApplication
import com.kucharski.michal.weatheracc.di.modules.*
import com.kucharski.michal.weatheracc.di.modules.ActivityModule
import com.kucharski.michal.weatheracc.di.modules.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FragmentsModule::class,
    AndroidSupportInjectionModule::class,
    RepositoryModule::class,
    DatabaseModule::class,
    ActivityModule::class,
    ContextModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class
    ]
)
interface AppComponent: AndroidInjector<WeatherApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<WeatherApplication>()
}