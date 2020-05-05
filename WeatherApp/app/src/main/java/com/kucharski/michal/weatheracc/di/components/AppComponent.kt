package com.kucharski.michal.weatheracc.di.components

import com.kucharski.michal.weatheracc.WeatherApplication
import com.kucharski.michal.weatheracc.di.modules.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        RepositoryModule::class,
        FragmentsModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        ActivityModule::class,
        ContextModule::class,
        ViewModelFactoryModule::class,
        RemoteModule::class
    ]
)
interface AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApplication>()
}