package com.mdmx.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RainDto(
    @SerializedName("3h")
    val rainPercent: Double
)