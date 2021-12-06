package com.example.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.repo.remote.response.WeatherResponse
import com.example.weatherapp.databinding.ListItemHeaderBinding
import com.example.weatherapp.util.loadWithGlide

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    private var cityList = emptyList<WeatherResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(
            ListItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount() = cityList.size

    fun submitList(cityList: List<WeatherResponse>?) {
        this.cityList = cityList ?: emptyList()
        notifyDataSetChanged()
    }

    fun getCityAtPosition(position: Int): WeatherResponse {
        return cityList[position]
    }

    class HeaderViewHolder(private val binding: ListItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherResponse: WeatherResponse) {
            with(binding) {
                mainBackgroundImage.loadWithGlide(weatherResponse.city.imageUrls?.androidImageUrls?.hdpiImageUrl)
                cityNameTextView.text = weatherResponse.city.name
                dateTextView.text = weatherResponse.city.modificationDate
                degreesTextView.text =
                    weatherResponse.weather.days[0].hourlyWeather[0].temperature.toString()
                timeTextView.text = weatherResponse.weather.days[0].hourlyWeather[0].hour.toString()
            }
        }
    }
}