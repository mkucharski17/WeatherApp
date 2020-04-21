package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.models.WeatherHourForecast
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import kotlinx.android.synthetic.main.item_hour_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*

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
                tvHour.text = SimpleDateFormat("HH:mm").format(Date(forecast.dt.toLong()*1000))
                tvDescription.text = forecast.weather.firstOrNull()?.description
                tvHourTemperature.text = forecast.main.temp.toInt().toString() + "°"
                setOnClickListener{listener}
            }
        }
    }

}