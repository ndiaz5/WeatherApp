package com.example.weatherapp.repo.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IosImageUrls(
    val imageUrl: String?
)
