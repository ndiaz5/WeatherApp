package com.example.weatherapp.util

sealed class ApiState<out T> {
    object Loading: ApiState<Nothing>()
    data class Success<T>(val data: T): ApiState<T>()
    data class Error<T>(val message: String): ApiState<T>()
}