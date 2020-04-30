package com.kucharski.michal.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.viewModels.SplashViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.splash_fragment.view.*
import javax.inject.Inject

class SplashFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SplashViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.splash_fragment, container, false)
            .apply {
                viewModel.proceed.observe(viewLifecycleOwner, Observer {

                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToForecastListFragment())



                })
            }
    }
}
