package com.example.weatherapp.repo.remote.response

import com.example.weatherapp.repo.remote.response.City
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityResponse(
    val cities: List<City>
)
