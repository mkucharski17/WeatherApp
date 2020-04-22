package com.kucharski.michal.weatheracc.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.DetailsAdapter
import com.kucharski.michal.weatheracc.adapters.HourlyWeatherAdapter
import com.kucharski.michal.weatheracc.adapters.WeeklyWeatherAdapter
import com.kucharski.michal.weatheracc.models.WeatherHourForecast
import com.kucharski.michal.weatheracc.viewModels.DetailsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.details_fragment.view.*
import kotlinx.android.synthetic.main.details_fragment.view.rvWeeklyForecast
import kotlinx.android.synthetic.main.details_fragment.view.tvTemperature
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import java.text.SimpleDateFormat

import java.util.*
import javax.inject.Inject


class DetailsFragment : DaggerFragment() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailsViewModel> { factory }

    private val weeklyWeatherAdapter by lazy {
        WeeklyWeatherAdapter{ weatherHourForecast: WeatherHourForecast, view: View ->

            rvWeeklyForecast.forEach {
                it.setBackgroundResource(R.drawable.radius_white_blur_rectangle)
            }
            view.setBackgroundResource(R.drawable.radius_white_rectangle)
            viewModel.updateDetailList(weatherHourForecast)
            viewModel.updateHourlyAndDetails(view.tvDayOfWeek.text.toString())

        }
    }
    private val hourlyWeatherAdapter by lazy {
        HourlyWeatherAdapter{
        }
    }

    private val detailsAdapter by lazy {
        DetailsAdapter{}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false).apply {
            tvTemperature.text = args.currentTemperature.toString() + "°"

            rvWeeklyForecast.adapter = weeklyWeatherAdapter
            rvHourlyForecast.adapter = hourlyWeatherAdapter
            rvDetails.adapter = detailsAdapter
            with(viewModel) {
                searchCity(args.cityId)
                hourlyWeatherForecast.observe(viewLifecycleOwner, Observer {
                    tvTime.text = getCurrentTime(it.city.timezone)
                    tvCityName.text = it.city.name + ", " + it.city.country
                    tvDescription.text = createDescription(it.list[0].main.temp_max, it.list[0].main.temp_min)

                })
                dailyList.observe(viewLifecycleOwner, Observer {
                    weeklyWeatherAdapter.submitList(it)
                })

                dayHourlyForecast.observe(viewLifecycleOwner, Observer {
                    hourlyWeatherAdapter.submitList(it)
                })

                detailsList.observe(viewLifecycleOwner, Observer {
                    detailsAdapter.submitList(it)
                })
            }
        }
    }

    private fun createDescription(tempMax: Double, tempMin: Double): String {
        var prefix = ""
        if (args.currentWeatherDescription != null) prefix =
            args.currentWeatherDescription + " currently."

        return prefix.capitalize() + " The high will be " + tempMax.toInt() + "°. " +
                "The low will be" +  tempMin.toInt() + "°. "
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
