package com.mdmx.weatherapp.domain.repository

import com.mdmx.weatherapp.common.Resource
import com.mdmx.weatherapp.domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(zip: Int): Flow<Resource<WeatherForecast>>
}