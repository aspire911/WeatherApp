package com.mdmx.weatherapp.domain.model

data class Data(
    val pressure: Int,
    val humidity: Int,
    val temp: Double,
    val dt: Long,
    val weatherDescription: String,
    val weatherIcon: String,
    val windSpeed: Double
)
