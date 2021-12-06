package com.example.weatherapp.repo.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    val alternatenames: String,
    val dem: Int,
    val longitude: Double,
    val geonameid: Int,
    @get:Json(name = "admin3 code")
    val admin3Code: Int?,
    val elevation: Int,
    val timezone: String,
    val asciiname: String,
    @get:Json(name = "modification date")
    val modificationDate: String,
    @get:Json(name = "country code")
    val countryCode: String,
    @get:Json(name = "admin4 code")
    val admin4Code: String,
    @get:Json(name = "feature code")
    val featureCode: String,
    val latitude: Double,
    val name: String,
    @get:Json(name = "imageURLs")
    val imageUrls: ImageUrls?,
    val population: Long,
    @get:Json(name = "admin2 code")
    val admin2Code: Int,
    val cc2: String,
    @get:Json(name = "admin1 code")
    val admin1Code: String,
    @get:Json(name = "feature class")
    val featureClass: String
)
