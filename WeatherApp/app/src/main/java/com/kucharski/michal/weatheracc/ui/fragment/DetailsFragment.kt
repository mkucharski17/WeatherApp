package com.kucharski.michal.weatheracc.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.viewModels.DetailsViewModel
import com.kucharski.michal.weatheracc.viewModels.ForecastListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.details_fragment.view.*
import javax.inject.Inject

class DetailsFragment : DaggerFragment() {



    @Inject
    lateinit var factory: ViewModelProvider.Factory
    val args: DetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailsViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false).apply {
            with(viewModel){
                hourlyWeatherForecast.observe(viewLifecycleOwner, Observer {
                    cityId.text = it.toString()
                })
            }
        }
    }



}
