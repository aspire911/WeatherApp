package com.mdmx.weatherapp.presentation

import com.mdmx.weatherapp.domain.model.Data

data class DataState(
    val city: String = "",
    val weatherData:  List<Data> = emptyList(),
    val massage: String = "",
    val isLoading: Boolean = false
)
