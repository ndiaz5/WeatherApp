package com.example.weatherapp.repo.remote.response

import com.example.weatherapp.repo.remote.response.City
import com.example.weatherapp.repo.remote.response.Weather
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val city: City,
    val weather: Weather
)
