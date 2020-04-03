package com.kucharski.michal.weatheracc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kucharski.michal.weatheracc.ui.fragment.SplashFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
