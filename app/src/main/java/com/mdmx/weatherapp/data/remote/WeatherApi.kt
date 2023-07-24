package com.mdmx.weatherapp.data.remote

import com.mdmx.weatherapp.data.remote.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/forecast")
    suspend fun getWeatherData(
        @Query("zip") zip: Int,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): WeatherForecastDto
}