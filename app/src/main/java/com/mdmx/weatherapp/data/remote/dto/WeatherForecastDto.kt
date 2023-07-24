package com.mdmx.weatherapp.data.remote.dto

import com.mdmx.weatherapp.data.local.entity.WeatherEntity

data class WeatherForecastDto(
    val city: CityDto,
    val cnt: Int,
    val cod: String,
    val list: List<DataDto>,
    val message: Int
) {
    fun toWeatherEntity(zip: Int) : WeatherEntity {
        return WeatherEntity(
            weatherData = list.map { it.toData() },
            city = city.name,
            zip = zip
        )
    }
}