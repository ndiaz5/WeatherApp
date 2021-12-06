package com.example.weatherapp.repo.remote.response


import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import com.example.weatherapp.R
import com.example.weatherapp.repo.remote.response.Hourly
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Day(
    val dayOfTheWeek: Int,
    val weatherType: String,
    val low: Int,
    val hourlyWeather: List<Hourly>,
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
    fun getDayStringResource(): Int {
        return when (dayOfTheWeek) {
            0 -> R.string.monday
            1 -> R.string.tuesday
            2 -> R.string.wednesday
            3 -> R.string.thursday
            4 -> R.string.friday
            5 -> R.string.saturday
            6 -> R.string.sunday
            else -> R.string.non_existent
        }
    }

    fun getDrawable(context: Context): Drawable? {
        return when (weatherType) {
            "sunny" -> {
                AppCompatResources.getDrawable(context,
                    R.drawable.ic_icon_weather_active_ic_sunny_active)
            }
            "cloudy" -> {
                AppCompatResources.getDrawable(context,
                    R.drawable.ic_icon_weather_active_ic_cloudy_active)
            }
            "lightRain" -> {
                AppCompatResources.getDrawable(context,
                    R.drawable.ic_icon_weather_active_ic_light_rain_active)
            }
            "heavyRain" -> {
                AppCompatResources.getDrawable(context,
                    R.drawable.ic_icon_weather_active_ic_heavy_rain_active)
            }
            "partlyCloudy" -> {
                AppCompatResources.getDrawable(context,
                    R.drawable.ic_icon_weather_active_ic_partly_cloudy_active)
            }
            "snowSleet" -> {
                AppCompatResources.getDrawable(context,
                    R.drawable.ic_icon_weather_active_ic_snow_sleet_active)
            }
            else -> {
                null
            }
        }
    }
}
