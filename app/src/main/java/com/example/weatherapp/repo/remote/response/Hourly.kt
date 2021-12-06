package com.example.weatherapp.repo.remote.response

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weatherapp.R
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly(
    val weatherType: String,
    val windSpeed: Double,
    val temperature: Int,
    val humidity: Double,
    val hour: Int,
    val rainChance: Double,
) {

    @DrawableRes
    fun getDrawableResource(): Int {
        return when (weatherType) {
            "sunny" -> R.drawable.ic_icon_weather_active_ic_sunny_active
            "cloudy" -> R.drawable.ic_icon_weather_active_ic_cloudy_active
            "lightRain" -> R.drawable.ic_icon_weather_active_ic_light_rain_active
            "heavyRain" -> R.drawable.ic_icon_weather_active_ic_heavy_rain_active
            "partlyCloudy" -> R.drawable.ic_icon_weather_active_ic_partly_cloudy_active
            "snowSleet" -> R.drawable.ic_icon_weather_active_ic_snow_sleet_active
            else -> 0
        }
    }

    @StringRes
    fun getHourStringResource(): Int {
        return when (hour) {
            0 -> R.string.twelve_am
            1 -> R.string.one_am
            2 -> R.string.two_am
            3 -> R.string.three_am
            4 -> R.string.four_am
            5 -> R.string.five_am
            6 -> R.string.six_am
            7 -> R.string.seven_am
            8 -> R.string.eight_am
            9 -> R.string.nine_am
            10 -> R.string.ten_am
            11 -> R.string.eleven_am
            12 -> R.string.twelve_pm
            13 -> R.string.one_pm
            14 -> R.string.two_pm
            15 -> R.string.three_pm
            16 -> R.string.four_pm
            17 -> R.string.five_pm
            18 -> R.string.six_pm
            19 -> R.string.seven_pm
            20 -> R.string.eight_pm
            21 -> R.string.nine_pm
            22 -> R.string.ten_pm
            23 -> R.string.eleven_pm
            else -> R.string.non_existent
        }
    }
}
