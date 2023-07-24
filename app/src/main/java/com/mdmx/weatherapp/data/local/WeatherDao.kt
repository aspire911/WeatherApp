package com.mdmx.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mdmx.weatherapp.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Upsert
    suspend fun insertWeatherData(weather: WeatherEntity)

    @Query("DELETE FROM weather WHERE zip = :zip")
    suspend fun deleteWeatherData(zip: Int)

    @Query("SELECT * FROM weather WHERE zip = :zip")
    suspend fun getWeatherData(zip: Int): WeatherEntity?
}