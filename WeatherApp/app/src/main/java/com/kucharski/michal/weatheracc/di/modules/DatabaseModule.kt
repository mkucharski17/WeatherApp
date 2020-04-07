package com.kucharski.michal.weatheracc.di.modules

import android.content.Context
import com.kucharski.michal.weatheracc.repository.local.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context) = AppDatabase.getInstance(context)
}