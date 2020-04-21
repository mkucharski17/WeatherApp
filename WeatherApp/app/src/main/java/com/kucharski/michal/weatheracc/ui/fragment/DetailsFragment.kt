package com.kucharski.michal.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.HourlyWeatherAdapter
import com.kucharski.michal.weatheracc.adapters.WeeklyWeatherAdapter
import com.kucharski.michal.weatheracc.viewModels.DetailsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.details_fragment.view.*
import java.text.SimpleDateFormat

import java.util.*
import javax.inject.Inject

class DetailsFragment : DaggerFragment() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailsViewModel> { factory }

    private val weeklyWeatherAdapter by lazy {
        WeeklyWeatherAdapter{}
    }
    private val hourlyWeatherAdapter by lazy {
        HourlyWeatherAdapter{}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false).apply {
            tvTemperature.text = args.currentTemperature.toString() + "°"
            tvDescription.text = createDescription()
            rvWeeklyForecast.adapter = weeklyWeatherAdapter
            rvHourlyForecast.adapter = hourlyWeatherAdapter
            with(viewModel) {
                searchCity(args.cityId)
                hourlyWeatherForecast.observe(viewLifecycleOwner, Observer {
                    tvTime.text = getCurrentTime(it.city.timezone)
                    tvCityName.text = it.city.name + ", " + it.city.country

                })
                dailyList.observe(viewLifecycleOwner, Observer {
                    weeklyWeatherAdapter.submitList(it)
                })

                dayHourlyForecast.observe(viewLifecycleOwner, Observer {
                    hourlyWeatherAdapter.submitList(it)
                })
            }
        }
    }

    private fun createDescription(): String {
        var prefix = ""
        if (args.currentWeatherDescription != null) prefix =
            args.currentWeatherDescription + " currently."

        return prefix.capitalize() + " The high will be " +  "°. " +
                "Tonight with a low of "  + "°. "
    }

    private fun getCurrentTime(timeZone: Int): String {
        val formatter = SimpleDateFormat("HH:mm MMM d", Locale.US)
        val formattedDate = formatter.format(Date(Date().time + timeZone * 1000))
        return formattedDate + addDateEnd(formattedDate.last().toInt())
    }

    private fun addDateEnd(day: Int): String {
        return when {
            day in 11..20 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }
    }


}
