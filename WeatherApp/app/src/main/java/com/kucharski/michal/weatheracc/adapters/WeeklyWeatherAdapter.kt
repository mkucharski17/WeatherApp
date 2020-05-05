package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.utils.getDayOfWeek
import com.kucharski.michal.weatheracc.models.WeatherHourForecast
import kotlinx.android.synthetic.main.item_daily_forecast.view.*
import java.util.*


class WeeklyWeatherAdapter (private val listener: (WeatherHourForecast,View) -> Unit
) : ListAdapter<WeatherHourForecast, WeeklyWeatherAdapter.WeeklyWeatherViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeeklyWeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        )

    override fun onBindViewHolder(holder: WeeklyWeatherViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherHourForecast>() {
            override fun areItemsTheSame(oldItem: WeatherHourForecast, newItem: WeatherHourForecast) =
                oldItem.dt == newItem.dt

            override fun areContentsTheSame(oldItem: WeatherHourForecast, newItem: WeatherHourForecast) =
                oldItem == newItem
        }
    }

    class WeeklyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forecast: WeatherHourForecast, listener: (WeatherHourForecast,View) -> Unit) {
            itemView.apply {
                if(getDayOfWeek(forecast.dt) == getDayOfWeek(
                        (Date().time / 1000).toInt()
                    )
                )
                    itemView.setBackgroundResource(R.drawable.radius_white_rectangle)

                forecast.weather.firstOrNull()?.let{
                    when(it.icon){
                        "50d" -> ivWeatherIcon.setImageResource(R.drawable.ic_fog)
                        "01d" -> ivWeatherIcon.setImageResource(R.drawable.ic_sun)
                        "03d" -> ivWeatherIcon.setImageResource(R.drawable.ic_cloud)
                        "10d" -> ivWeatherIcon.setImageResource(R.drawable.ic_rain)
                        "11d" -> ivWeatherIcon.setImageResource(R.drawable.ic_thunder)
                        "13d" -> ivWeatherIcon.setImageResource(R.drawable.ic_snow)
                        else -> ivWeatherIcon.setImageResource(R.drawable.ic_sun_cloud)
                    }
                    tvDayOfWeek.text =
                        getDayOfWeek(
                            forecast.dt
                        )
                    tvTemperature.text = "${forecast.main.temp.toInt()}°"
                    setOnClickListener { listener(forecast,itemView) }
                }
            }
        }
    }

}