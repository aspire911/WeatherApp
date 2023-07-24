package com.mdmx.weatherapp.di

import android.app.Application
import androidx.room.Room
import coil.ImageLoader
import com.google.gson.Gson
import com.mdmx.weatherapp.R
import com.mdmx.weatherapp.common.Constants.BASE_URL
import com.mdmx.weatherapp.common.Constants.CROSSFADE_DURATION
import com.mdmx.weatherapp.common.Constants.DB_NAME
import com.mdmx.weatherapp.data.local.Converters
import com.mdmx.weatherapp.data.local.WeatherDatabase
import com.mdmx.weatherapp.data.remote.WeatherApi
import com.mdmx.weatherapp.data.repository.WeatherRepositoryImpl
import com.mdmx.weatherapp.domain.repository.WeatherRepository
import com.mdmx.weatherapp.domain.use_case.GetWeather
import com.mdmx.weatherapp.data.util.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeather {
        return GetWeather(repository)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        db: WeatherDatabase,
        api: WeatherApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun providerWeatherDatabase(app: Application): WeatherDatabase {
        return Room.databaseBuilder(
            app, WeatherDatabase::class.java, DB_NAME
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .crossfade(true)
            .crossfade(CROSSFADE_DURATION)
            .build()
    }
}