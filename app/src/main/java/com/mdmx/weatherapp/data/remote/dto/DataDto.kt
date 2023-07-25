package com.mdmx.weatherapp.data.remote.dto

import com.mdmx.weatherapp.common.Constants.DATE_FORMAT
import com.mdmx.weatherapp.domain.model.Data
import java.util.Locale

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
        val sdf = java.text.SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val date = java.util.Date(dt * 1000L)
        return Data(
            pressure = main.pressure,
            humidity = main.humidity,
            temp = main.temp,
            dt = dt,
            dateFormatted = sdf.format(date),
            weatherDescription = weather[0].description,
            weatherIcon = weather[0].icon,
            windSpeed = wind.speed
        )
    }
}