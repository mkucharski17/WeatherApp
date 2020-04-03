package com.kucharski.michal.weatheracc.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.SavedCitiesAdapter
import com.kucharski.michal.weatheracc.di.Injector
import com.kucharski.michal.weatheracc.models.CityWeatherModel
import com.kucharski.michal.weatheracc.viewModels.ForecastListViewModel
import com.kucharski.michal.weatheracc.viewModels.SplashViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.forecast_list_fragment.*
import kotlinx.android.synthetic.main.forecast_list_fragment.view.*
import javax.inject.Inject


class ForecastListFragment : DaggerFragment() {
    @Inject
    lateinit var factory  : ViewModelProvider.Factory

    private val viewModel by viewModels<ForecastListViewModel> { factory }

    private val citiesAdapter by lazy{
        SavedCitiesAdapter{
            Toast.makeText(context,it.name,Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object: OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
        val rootView = inflater.inflate(R.layout.forecast_list_fragment, container, false)
        rootView.rvCities.adapter = citiesAdapter
        rootView.apply{
            viewModel.weatherList.observe(viewLifecycleOwner, Observer {
                citiesAdapter.submitList(it)
            })
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ForecastListViewModel::class.java)
        addButton.setOnClickListener{
            findNavController().navigate(R.id.action_forecastListFragment_to_temporaryFragment)
        }
    }

}
