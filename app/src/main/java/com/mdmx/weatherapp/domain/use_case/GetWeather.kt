package com.mdmx.weatherapp.domain.use_case

import com.mdmx.weatherapp.common.Constants
import com.mdmx.weatherapp.common.Constants.ZIP_CODE_LENGTH
import com.mdmx.weatherapp.common.Resource
import com.mdmx.weatherapp.domain.model.WeatherForecast
import com.mdmx.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeather(
    private val repository: WeatherRepository
) {

    operator fun invoke(zip: String): Flow<Resource<WeatherForecast>> {
        if(zip.length != ZIP_CODE_LENGTH) return flow {}
        if(zip.filter { it.isDigit() }.length != ZIP_CODE_LENGTH) return flow {
            emit(Resource.Failure(data = null, massage = Constants.ERROR_ZIP))
        }
        return repository.getWeather(zip.toInt())
    }
}