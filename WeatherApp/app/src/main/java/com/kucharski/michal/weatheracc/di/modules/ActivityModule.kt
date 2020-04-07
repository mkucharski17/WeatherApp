package com.kucharski.michal.weatheracc.di.modules

import com.kucharski.michal.weatheracc.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModule{

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

}
