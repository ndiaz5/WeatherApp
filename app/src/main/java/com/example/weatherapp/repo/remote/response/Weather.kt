package com.example.weatherapp.repo.remote.response

import com.example.weatherapp.repo.remote.response.Day
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Weather(
    val id: Int,
    val days: List<Day>
)