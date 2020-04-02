package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.SavedCitiesAdapter.SearchCitiesViewHolder
import com.kucharski.michal.weatheracc.models.CityWeatherModel
import kotlinx.android.synthetic.main.item_saved_city.view.*

class SavedCitiesAdapter(
    private val listener: (CityWeatherModel) -> Unit
) : ListAdapter<CityWeatherModel, SearchCitiesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchCitiesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_saved_city, parent, false)
        )

    override fun onBindViewHolder(holder: SearchCitiesViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CityWeatherModel>() {
            override fun areItemsTheSame(oldItem: CityWeatherModel, newItem: CityWeatherModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CityWeatherModel, newItem: CityWeatherModel) =
                oldItem == newItem
        }
    }


    class SearchCitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: CityWeatherModel, listener: (CityWeatherModel) -> Unit) {
            itemView.apply {
                when (city.status) {
                    "Sunny" -> {
                        itemContainer.setBackgroundResource(R.drawable.sunny)
                    }
                    "Clouds" -> {
                        itemContainer.setBackgroundResource(R.drawable.cloudy)
                    }
                    "Clear Sky" -> {
                        itemContainer.setBackgroundResource(R.drawable.clear_sky)
                    }
                    "Foggy" -> {
                        itemContainer.setBackgroundResource(R.drawable.foggy)
                    }
                    "Clear Night" -> {
                        itemContainer.setBackgroundResource(R.drawable.clear_night)
                    }
                }
                cityName.text = city.name
                date.text = city.date
                temperature.text = "${city.temperature} °C"
                setOnClickListener { listener(city) }
            }
        }
    }

}
