package com.kucharski.michal.weatheracc.di.modules

import com.kucharski.michal.weatheracc.ui.fragment.ForecastListFragment
import com.kucharski.michal.weatheracc.ui.fragment.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentsModule {
    @ContributesAndroidInjector
    internal abstract fun splashFragment(): SplashFragment

    @ContributesAndroidInjector
    internal abstract fun forecastFragment(): ForecastListFragment
}