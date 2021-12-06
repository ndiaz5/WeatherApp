package com.example.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ListItemHourBinding
import com.example.weatherapp.repo.remote.response.Hourly

class HourAdapter : RecyclerView.Adapter<HourAdapter.HourWeatherViewHolder>() {

    private var hourList = emptyList<Hourly>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourWeatherViewHolder {
        return HourWeatherViewHolder(
            ListItemHourBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HourWeatherViewHolder, position: Int) {
        holder.bind(hourList[position])
    }

    override fun getItemCount() = hourList.size

    fun submitList(hourList: List<Hourly>) {
        this.hourList = hourList
        notifyDataSetChanged()
    }

    inner class HourWeatherViewHolder(private val binding: ListItemHourBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hour: Hourly) {
            with(binding) {
                humidityTextView.text = hour.humidity.toString()
                rainTextView.text = hour.rainChance.toString()
                temperatureTextView.text = hour.temperature.toString()
                timeTextView.text = root.context.getString(hour.getHourStringResource())
                weatherImageView.setImageDrawable(
                    AppCompatResources.getDrawable(
                        root.context,
                        hour.getDrawableResource()
                    )
                )
                windTextView.text = hour.windSpeed.toString()
            }
        }
    }

}