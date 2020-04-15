package com.kucharski.michal.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.CitiesAdapter
import com.kucharski.michal.weatheracc.models.Units
import com.kucharski.michal.weatheracc.viewModels.ForecastListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.forecast_list_fragment.view.*
import kotlinx.android.synthetic.main.search_city_fragment.*
import javax.inject.Inject



class ForecastListFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<ForecastListViewModel> { factory }

    private val citiesAdapter by lazy {
        CitiesAdapter {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            findNavController().navigate(ForecastListFragmentDirections.actionForecastListFragmentToDetailsFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_list_fragment, container, false).apply {
            textSwitcher.setOnClickListener {
                viewModel.updateUnits()
            }
            rvCities.adapter = citiesAdapter
            addButton.setOnClickListener {
                findNavController().navigate(
                    ForecastListFragmentDirections.actionForecastListFragmentToSearchCityFragment()
                )
            }

            with(viewModel) {
                weatherList.observe(viewLifecycleOwner, Observer {
                    citiesAdapter.submitList(it)
                })
                units.observe(viewLifecycleOwner, Observer {
                    textSwitcher.setText(getString(if (it == Units.METRIC) R.string.units_metric else R.string.units_imperial))
                })
            }
        }
    }
}
