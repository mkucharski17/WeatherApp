package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.models.WeatherForecast
import com.kucharski.michal.weatheracc.utils.getDateMonthYear
import kotlinx.android.synthetic.main.item_saved_city.view.*


class CitiesAdapter(
    private val listener: (WeatherForecast, Boolean) -> Unit
) : ListAdapter<WeatherForecast, CitiesAdapter.CitiesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitiesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_saved_city, parent, false)
        )

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherForecast>() {
            override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast) =
                oldItem == newItem
        }
    }

    class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: WeatherForecast, listener: (WeatherForecast, Boolean) -> Unit) {
            itemView.apply {
                city.weather.firstOrNull()?.let {
                    when (it.icon.take(2).trim().toInt()) {
                        50 -> {
                            itemContainer.setBackgroundResource(R.drawable.foggy)
                            icWeather.setImageResource(R.drawable.ic_fog)
                        }
                        in 2..4 -> {
                            itemContainer.setBackgroundResource(R.drawable.cloudy)
                            icWeather.setImageResource(R.drawable.ic_sun_cloud)

                        }
                        1 -> {
                            if (it.icon[2] == 'd') {
                                if (city.main.temp > 30) {
                                    itemContainer.setBackgroundResource(R.drawable.sunny)
                                    icWeather.setImageResource(R.drawable.ic_orange_sun)
                                } else {
                                    itemContainer.setBackgroundResource(R.drawable.clear_sky)
                                    icWeather.setImageResource(R.drawable.ic_sun)
                                }
                            }else{
                                itemContainer.setBackgroundResource(R.drawable.clear_night)
                                icWeather.setImageResource(R.drawable.mock_icon)
                            }

                        }

                    }
                }
                tvCityName.text = city.name
                tvDate.text =
                    getDateMonthYear(city.dt)
                tvMaxMinTemp.text =
                    "${city.main.temp_max.toInt()}° / ${city.main.temp_min.toInt()}°"

                setOnClickListener { listener(city, false) }
                ivRemove.setOnClickListener { listener(city, true) }
            }
        }


    }
}