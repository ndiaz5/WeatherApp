package com.example.weatherapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.*
import com.example.weatherapp.repo.remote.response.City
import com.example.weatherapp.repo.remote.response.Day
import com.example.weatherapp.repo.remote.response.WeatherResponse
import com.example.weatherapp.R
import com.example.weatherapp.repo.WeatherRepo
import com.example.weatherapp.repo.remote.response.Hourly
import com.example.weatherapp.util.ApiState
import com.example.weatherapp.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepo,
     @ApplicationContext private val context: Context
) : BaseViewModel() {

    private var _cityState: MutableLiveData<CityState> = MutableLiveData(CityState())
    val cityState: LiveData<CityState> get() = _cityState

    private var _weatherState: MutableLiveData<WeatherState> = MutableLiveData(WeatherState())
    val weatherState: LiveData<WeatherState> get() = _weatherState

    init {
        fetchWeather()
    }

    fun fetchCity(cityName: String) {
        if (cityName.isBlank()) _cityState.value =
            CityState(error = context.getString(R.string.invalid_query))
        if (!isNetworkAvailable(context)) _cityState.value =
            CityState(error = context.getString(R.string.network_unavailable))
        viewModelScope.launch {
            repo.getCity(cityName).collect { state ->
                when (state) {
                    is ApiState.Loading -> {
                        _cityState.postValue(CityState(isLoading = true))
                    }
                    is ApiState.Success -> {
                        _cityState.postValue(CityState(cities = state.data.cities))
                    }
                    is ApiState.Error -> {
                        _cityState.postValue(CityState(error = state.message))
                    }
                }
            }
        }
    }

    fun fetchWeather(cityId: Int = 4054852, cityName: String = "") {
        if (!isNetworkAvailable(context)) _weatherState.value =
            _weatherState.value?.copy(error = context.getString(R.string.network_unavailable))
        viewModelScope.launch {
            repo.getWeather(cityId, cityName).collect { state ->
                val temp = _weatherState.value
                when (state) {
                    is ApiState.Loading -> {
                        _weatherState.postValue(temp?.copy(isLoading = true))
                    }
                    is ApiState.Success -> {
                        val currentWeather = temp?.weatherResponses?.filter { it.city != state.data.city } as MutableList<WeatherResponse>?
                        currentWeather?.add(0, state.data)

                        _weatherState.postValue(
                            WeatherState(
                                weatherResponses = currentWeather,
                                daily = state.data.weather.days,
                                hourly = state.data.weather.days[0].hourlyWeather
                            )
                        )
                    }
                    is ApiState.Error -> {
                        val isEmpty = temp?.weatherResponses?.isEmpty() ?: false
                        _weatherState.postValue(temp?.copy(
                            error = state.message,
                            isEmpty = isEmpty,
                            isLoading = false
                        ))
                    }
                }
            }
        }
    }

    fun emptyCityResults() {
        _cityState.value = CityState()
    }

    fun onCityScroll(weather: WeatherResponse) {
        _weatherState.value = weatherState.value?.copy(
            daily = weather.weather.days,
            hourly = weather.weather.days[0].hourlyWeather
        )
    }

    fun onSelectDay(index: Int) {
        val temp = weatherState.value
        _weatherState.value =
            weatherState.value?.copy(hourly = temp?.daily?.get(index)?.hourlyWeather!!)
    }

    data class CityState(
        val cities: List<City> = emptyList(),
        var isLoading: Boolean = false,
        var error: String = "",
    )

    data class WeatherState(
        val weatherResponses: List<WeatherResponse>? = emptyList(),
        val daily: List<Day> = emptyList(),
        val hourly: List<Hourly> = emptyList(),
        var isLoading: Boolean = false,
        var error: String = "",
        var isEmpty: Boolean = false
    )

}