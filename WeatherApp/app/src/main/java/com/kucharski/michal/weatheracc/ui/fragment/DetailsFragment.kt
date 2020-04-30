package com.kucharski.michal.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.DetailsAdapter
import com.kucharski.michal.weatheracc.adapters.HourlyWeatherAdapter
import com.kucharski.michal.weatheracc.adapters.WeeklyWeatherAdapter
import com.kucharski.michal.weatheracc.utils.getDayOfWeek
import com.kucharski.michal.weatheracc.utils.getHourMinutesMonthDay
import com.kucharski.michal.weatheracc.models.WeatherHourForecast
import com.kucharski.michal.weatheracc.viewModels.DetailsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.details_fragment.view.*
import kotlinx.android.synthetic.main.details_fragment.view.tvTemperature
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import java.util.*
import javax.inject.Inject


class DetailsFragment : DaggerFragment() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailsViewModel> { factory }

    private val weeklyWeatherAdapter by lazy {
        WeeklyWeatherAdapter { weatherHourForecast: WeatherHourForecast, view: View ->

            rvWeeklyForecast.forEach {
                it.setBackgroundResource(R.drawable.radius_white_blur_rectangle)
            }
            view.setBackgroundResource(R.drawable.radius_white_rectangle)
            viewModel.updateDetailList(weatherHourForecast)
            viewModel.updateDayHourlyForecast(view.tvDayOfWeek.text.toString())
        }
    }
    private val hourlyWeatherAdapter by lazy {
        HourlyWeatherAdapter {}
    }

    private val detailsAdapter by lazy {
        DetailsAdapter {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false).apply {
            rvWeeklyForecast.adapter = weeklyWeatherAdapter
            rvHourlyForecast.adapter = hourlyWeatherAdapter
            rvDetails.adapter = detailsAdapter
            tvTemperature.text = "${args.weatherForecast.main.temp.toInt()}°"

            args.weatherForecast.weather.firstOrNull()?.let {
                when (it.description) {

                    "clear sky" -> {
                        ivBackground.setImageResource(R.drawable.ic_details_sunny_background)
                        ivCorner.setImageResource(R.drawable.ic_orange_corner_sun)
                    }
                    "rain" -> {
                        ivBackground.setImageResource(R.drawable.ic_details_rainy_background)
                        ivCorner.setImageResource(R.drawable.image_clouds)
                        ivRain.setImageResource(R.drawable.image_rain)
                    }
                    "mist" -> {
                        ivBackground.setImageResource(R.drawable.ic_details_foggy_background)
                    }
                    "thunderstorm" -> {
                        ivCorner.setImageResource(R.drawable.image_clouds)
                        ivBackground.setImageResource(R.drawable.ic_details_thunder_background)
                        ivRain.setImageResource(R.drawable.image_rain)
                        ivThunder.setImageResource(R.drawable.image_thunder)
                    }
                    "snow" -> {
                        ivBackground.setImageResource(R.drawable.ic_details_snowy_background)
                        ivCorner.setImageResource(R.drawable.image_snow)
                    }
                    else -> {
                        ivBackground.setImageResource(R.drawable.ic_details_few_clouds_backgorund)
                        ivCorner.setImageResource(R.drawable.ic_corner_light_sun)
                    }
                }
            }

            with(viewModel) {
                searchCity(args.weatherForecast.id)
                hourlyWeatherForecast.observe(viewLifecycleOwner, Observer {
                    it.list.firstOrNull()?.let { weatherHourForecast ->
                        updateDetailList(weatherHourForecast)
                        viewModel.updateDayHourlyForecast(
                            getDayOfWeek(
                                weatherHourForecast.dt
                            )
                        )
                    }

                    tvTime.text =
                        getHourMinutesMonthDay(
                            it.city.timezone
                        )
                    tvCityName.text = "${it.city.name} ${it.city.country}"

                    tvDescription.text = createDescription(
                        viewModel.findMaxTemp(
                            getDayOfWeek(
                                Date().time.toInt()
                            )
                        ),
                        viewModel.findMinTemp(
                            getDayOfWeek(
                                Date().time.toInt()
                            )
                        )
                    )

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


    private fun createDescription(maxTemp: Int?, minTemp: Int?): String {
        var prefix = ""
        args.weatherForecast.weather.firstOrNull()?.description.let {
            prefix = "$it currently. "
        }
        return "${prefix.capitalize()} The high will be $maxTemp°. The low will be  $minTemp°. "
    }

}
