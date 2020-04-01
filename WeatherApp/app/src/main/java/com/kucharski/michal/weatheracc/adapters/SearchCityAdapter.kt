package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.models.SearchCityModel
import kotlinx.android.synthetic.main.item_search_city.view.*

class SearchCityAdapter(
    private val listener: (SearchCityModel) -> Unit
) : ListAdapter<SearchCityModel, SearchCityAdapter.SearchViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_city, parent, false)
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchCityModel>() {
            override fun areItemsTheSame(oldItem: SearchCityModel, newItem: SearchCityModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SearchCityModel, newItem: SearchCityModel) =
                oldItem == newItem
        }
    }

    class SearchViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        fun bind(city: SearchCityModel, listener: (SearchCityModel) -> Unit) {
            itemView.apply {
                cityName.text = city.name
                Country.text = ", ${city.country}"
                setOnClickListener { listener(city) }

            }
        }
    }
}