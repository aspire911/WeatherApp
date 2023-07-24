package com.mdmx.weatherapp.data.repository


import com.mdmx.weatherapp.common.Constants.ERROR_CONNECTION
import com.mdmx.weatherapp.common.Constants.ERROR_DATA
import com.mdmx.weatherapp.common.Constants.ERROR_ZIP
import com.mdmx.weatherapp.common.Constants.OPEN_WEATHER_API_KAY
import com.mdmx.weatherapp.common.Constants.UNITS
import com.mdmx.weatherapp.common.Resource
import com.mdmx.weatherapp.data.local.WeatherDao
import com.mdmx.weatherapp.data.remote.WeatherApi
import com.mdmx.weatherapp.domain.model.WeatherForecast
import com.mdmx.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {
    override fun getWeather(zip: Int): Flow<Resource<WeatherForecast>> = flow {
        emit(Resource.Loading())
        val localWeatherData = dao.getWeatherData(zip)?.toWeatherForecast()
        try {
            val remoteWeatherData = api.getWeatherData(zip, OPEN_WEATHER_API_KAY, UNITS)
            dao.deleteWeatherData(zip)
            dao.insertWeatherData(remoteWeatherData.toWeatherEntity(zip))
        } catch (e: HttpException) {
            emit(
                Resource.Failure(
                    massage = ERROR_ZIP,
                    data = localWeatherData
                )
            )
            return@flow
        } catch (e: IOException) {
            var massage = ERROR_CONNECTION
            localWeatherData?.let { massage += ERROR_DATA }
            emit(
                Resource.Failure(
                    massage = massage,
                    data = localWeatherData
                )
            )
            return@flow
        }
        val newWeatherInfo = dao.getWeatherData(zip)?.toWeatherForecast()
        newWeatherInfo?.let { emit(Resource.Success(data = it)) }
    }
}