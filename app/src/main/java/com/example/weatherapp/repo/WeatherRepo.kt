package com.example.weatherapp.repo

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.repo.remote.WeatherService
import com.example.weatherapp.util.ApiState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val weatherService: WeatherService,
    @ApplicationContext private val context: Context
) {
    fun getCity(search: String? = "Mos Eisley") =
        flow {
            emit(ApiState.Loading)
            val state = try {
                val response = weatherService.getCity(search)
                if (response.isSuccessful && response.body() != null) {
                    ApiState.Success(response.body()!!)
                } else {
                    ApiState.Error("Request failed.")
                }
            } catch (ex: Exception) {
                ApiState.Error(context.getString(R.string.unexpected_error))
            }
            emit(state)
        }

    fun getWeather(cityId: Int, cityName: String) = flow {
        emit(ApiState.Loading)
        val state = try {
            val response = weatherService.getWeatherById(cityId)
            if (response.isSuccessful && response.body() != null) {
                ApiState.Success(response.body()!!)
            } else {
                ApiState.Error(context.getString(R.string.unsuccessful_request).format(cityName))
            }
        } catch (ex: Exception) {
            ApiState.Error(context.getString(R.string.unexpected_error))
        }
        emit(state)
    }
}