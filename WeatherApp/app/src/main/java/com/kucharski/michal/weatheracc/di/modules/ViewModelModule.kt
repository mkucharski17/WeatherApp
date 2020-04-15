package com.kucharski.michal.weatheracc.di.modules

import androidx.lifecycle.ViewModel
import com.kucharski.michal.weatheracc.viewModels.DetailsViewModel
import com.kucharski.michal.weatheracc.viewModels.ForecastListViewModel
import com.kucharski.michal.weatheracc.viewModels.SearchCityViewModel
import com.kucharski.michal.weatheracc.viewModels.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(key = ForecastListViewModel::class)
    abstract fun bindForecastListViewModel(viewModel: ForecastListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = SearchCityViewModel::class)
    abstract fun bindSearchCityViewModel(viewModel: SearchCityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(key = DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel



}