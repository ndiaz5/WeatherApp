package com.example.weatherapp.repo.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageUrls(
    @get:Json(name = "iOSImageURLs")
    val iosImageUrls: IosImageUrls?,
    @get:Json(name = "androidImageURLs")
    val androidImageUrls: AndroidImageUrls?
)
