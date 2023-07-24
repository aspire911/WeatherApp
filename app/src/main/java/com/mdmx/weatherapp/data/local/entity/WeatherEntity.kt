package com.mdmx.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mdmx.weatherapp.domain.model.Data
import com.mdmx.weatherapp.domain.model.WeatherForecast

@Entity( tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val zip: Int,
    val city: String,
    val weatherData: List<Data>,
) {
    fun toWeatherForecast() : WeatherForecast {
        return WeatherForecast(
            zip = zip,
            city = city,
            weatherData = weatherData
        )
    }
}
