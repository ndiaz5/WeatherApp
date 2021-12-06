package com.example.weatherapp.repo.remote


import com.example.weatherapp.repo.remote.response.CityResponse
import com.example.weatherapp.repo.remote.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("cities")
    suspend fun getCity(
        @Query("search") search: String? = null
    ): Response<CityResponse>

    @GET("cities/{cityID}")
    suspend fun getWeatherById(
        @Path("cityID") cityId: Int = 1
    ): Response<WeatherResponse>
}