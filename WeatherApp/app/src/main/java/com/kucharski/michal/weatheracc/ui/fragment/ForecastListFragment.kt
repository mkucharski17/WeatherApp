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
import androidx.navigation.fragment.findNavController

import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.SavedCitiesAdapter
import com.kucharski.michal.weatheracc.di.Injector
import com.kucharski.michal.weatheracc.models.CityWeatherModel
import com.kucharski.michal.weatheracc.viewModels.ForecastListViewModel
import com.kucharski.michal.weatheracc.viewModels.SplashViewModel
import kotlinx.android.synthetic.main.forecast_list_fragment.*
import kotlinx.android.synthetic.main.forecast_list_fragment.view.*




class ForecastListFragment : Fragment() {
    private val factory by lazy { Injector.provideFactory(context!!) }
    private val viewModel by viewModels<ForecastListViewModel> { factory }
    private val citiesAdapter by lazy{
        SavedCitiesAdapter{
            Toast.makeText(context,it.name,Toast.LENGTH_SHORT).show()
        }
    }
    private val citiesList = mutableListOf(
        CityWeatherModel(
            "1",
            "Warszawa",
            status = "Clear Sky",
            temperature = 15
        ),
        CityWeatherModel(
            "2",
            "Wrocław",
            status = "Clouds",
            temperature = 12
        ),
        CityWeatherModel(
            "3",
            "Poznań",
            status = "Clear Sky",
            temperature = 10
        ),
        CityWeatherModel(
            "4",
            "Gdańsk",
            status = "Clouds",
            temperature = 20
        ),
        CityWeatherModel(
            "5",
            "Los Angeles",
            status = "Clear Night",
            temperature = 22
        ),
        CityWeatherModel(
            "6",
            "Miami",
            status = "Foggy",
            temperature = 30
        ),
        CityWeatherModel(
            "7",
            "Las Vegas",
            status = "Foggy",
            temperature = 11
        ),
        CityWeatherModel(
            "8",
            "San Francisco",
            status = "Foggy",
            temperature = 12
        )

    )

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
        rootView.rvCities.adapter = citiesAdapter.apply { submitList(citiesList) }
        rootView.apply{
            viewModel.weatherList.observe(viewLifecycleOwner, Observer {})
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(ForecastListViewModel::class.java)
        addButton.setOnClickListener{
            findNavController().navigate(R.id.action_forecastListFragment_to_temporaryFragment)
        }
    }

}
