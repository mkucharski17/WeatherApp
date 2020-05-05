package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.models.WeatherForecast
import kotlinx.android.synthetic.main.item_search_city.view.*

class SearchCityAdapter(
    private val listener: (WeatherForecast) -> Unit
) : ListAdapter<WeatherForecast, SearchCityAdapter.CitySearchViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitySearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_city, parent, false)
        )

    override fun onBindViewHolder(holder: CitySearchViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherForecast>() {
            override fun areItemsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WeatherForecast, newItem: WeatherForecast) =
                oldItem == newItem
        }
    }

    class CitySearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: WeatherForecast, listener: (WeatherForecast) -> Unit) {
            itemView.apply {
                tvTitle.text = "${city.name}, ${city.sys.country}"
                setOnClickListener { listener(city) }
            }
        }
    }
}