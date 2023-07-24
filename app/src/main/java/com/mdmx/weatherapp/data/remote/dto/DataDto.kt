package com.mdmx.weatherapp.data.remote.dto

import com.mdmx.weatherapp.domain.model.Data

data class DataDto(
    val dt: Long,
    val dt_txt: String,
    val main: MainDto,
    val pop: Double,
    val rain: RainDto?,
    val visibility: Int,
    val weather: ArrayList<WeatherDto>,
    val wind: WindDto
) {
    fun toData(): Data {
        return Data(
            pressure = main.pressure,
            humidity = main.humidity,
            temp = main.temp,
            dt = dt,
            weatherDescription = weather[0].description,
            weatherIcon = weather[0].icon,
            windSpeed = wind.speed
        )
    }
}