package com.example.weatherapp.repo.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AndroidImageUrls(
    @get:Json(name = "mdpiImageURL")
    val mdpiImageUrl: String,
    @get:Json(name = "xhdpiImageURL")
    val xhdpiImageUrl: String,
    @get:Json(name = "hdpiImageURL")
    val hdpiImageUrl: String
)
