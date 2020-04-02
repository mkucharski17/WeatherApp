package com.kucharski.michal.weatheracc.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.di.Injector
import com.kucharski.michal.weatheracc.viewModels.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private val factory by lazy { Injector.provideFactory(context!!) }
    private val viewModel by viewModels<SplashViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
            delay(2500)
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToForecastListFragment())
        }
    }

}
