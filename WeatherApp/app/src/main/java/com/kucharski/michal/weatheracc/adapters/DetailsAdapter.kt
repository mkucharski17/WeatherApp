package com.kucharski.michal.weatheracc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.models.WeatherHourForecast
import kotlinx.android.synthetic.main.item_details_row.view.*
import kotlinx.android.synthetic.main.item_hour_forecast.view.*
import java.text.SimpleDateFormat
import java.util.*

class DetailsAdapter (private val listener: (Pair<String,String>) -> Unit
) : ListAdapter<Pair<String,String>, DetailsAdapter.WeatherDetailsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherDetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_details_row, parent, false)
        )

    override fun onBindViewHolder(holder: WeatherDetailsViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pair<String,String>>() {
            override fun areItemsTheSame(oldItem: Pair<String,String>, newItem: Pair<String,String>) =
                oldItem.first == newItem.first &&  oldItem.second == newItem.second

            override fun areContentsTheSame(oldItem: Pair<String,String>, newItem: Pair<String,String>) =
                oldItem == newItem
        }
    }

    class WeatherDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(detail: Pair<String,String>, listener: (Pair<String,String>) -> Unit) {
            itemView.apply {
                tvDetail.text = detail.first
                tvDetailValue.text = detail.second
                setOnClickListener{listener}
            }
        }
    }

}