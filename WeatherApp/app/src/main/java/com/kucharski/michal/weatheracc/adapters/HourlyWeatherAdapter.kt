package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.utils.getHourAndMinutes
import com.kucharski.michal.weatheracc.models.WeatherHourForecast
import kotlinx.android.synthetic.main.item_hour_forecast.view.*

class HourlyWeatherAdapter (private val listener: (WeatherHourForecast) -> Unit
) : ListAdapter<WeatherHourForecast, HourlyWeatherAdapter.HourlyWeatherViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HourlyWeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_hour_forecast, parent, false)
        )

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherHourForecast>() {
            override fun areItemsTheSame(oldItem: WeatherHourForecast, newItem: WeatherHourForecast) =
                oldItem.dt == newItem.dt

            override fun areContentsTheSame(oldItem: WeatherHourForecast, newItem: WeatherHourForecast) =
                oldItem == newItem
        }
    }

    class HourlyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forecast: WeatherHourForecast, listener: (WeatherHourForecast) -> Unit) {
            itemView.apply {
                tvHour.text =
                    getHourAndMinutes(
                        forecast.dt
                    )
                tvDescription.text = forecast.weather.firstOrNull()?.description
                tvHourTemperature.text = "${forecast.main.temp.toInt()}°"
            }
        }
    }

}