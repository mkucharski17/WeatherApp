package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.models.WeatherForecast
import kotlinx.android.synthetic.main.item_saved_city.view.*

class CitiesAdapter(
    private val listener: (WeatherForecast) -> Unit
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
        fun bind(city: WeatherForecast, listener: (WeatherForecast) -> Unit) {
            itemView.apply {
                when(city.weather.firstOrNull()?.description){
                    "clear sky" -> itemContainer.setBackgroundResource(R.drawable.sunny)
                    "few clouds" -> itemContainer.setBackgroundResource(R.drawable.clear_sky)
                    "scattered clouds" -> itemContainer.setBackgroundResource(R.drawable.cloudy)
                    "mist" -> itemContainer.setBackgroundResource(R.drawable.foggy)
                    else -> itemContainer.setBackgroundResource(R.drawable.clear_sky)
                }
                tvCityName.text = city.name
                tvDate.text = city.weather.firstOrNull()?.description
                tvTemperature.text = "${city.main.temp.toInt()}°"
                setOnClickListener { listener(city) }
            }
        }
    }
}