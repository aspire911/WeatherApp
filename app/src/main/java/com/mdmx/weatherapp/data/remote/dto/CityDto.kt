package com.mdmx.weatherapp.data.remote.dto

data class CityDto(
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)