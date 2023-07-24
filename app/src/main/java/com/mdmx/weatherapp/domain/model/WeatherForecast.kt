package com.mdmx.weatherapp.domain.model

data class WeatherForecast(
    val zip: Int,
    val city: String,
    val weatherData: List<Data>,
)
