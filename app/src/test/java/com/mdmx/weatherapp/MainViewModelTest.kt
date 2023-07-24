package com.mdmx.weatherapp

import com.mdmx.weatherapp.common.Constants.ERROR_CONNECTION
import com.mdmx.weatherapp.common.Constants.ERROR_DATA
import com.mdmx.weatherapp.common.Constants.ERROR_ZIP
import com.mdmx.weatherapp.common.Resource
import com.mdmx.weatherapp.domain.model.Data
import com.mdmx.weatherapp.domain.model.WeatherForecast
import com.mdmx.weatherapp.domain.use_case.GetWeather
import com.mdmx.weatherapp.presentation.DataState
import com.mdmx.weatherapp.presentation.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val getWeather = mock<GetWeather>()
    private val viewModel = WeatherViewModel(getWeather = getWeather)
    private val response = mock<WeatherForecast>()
    private val data = mock<Data>()
    private val dispatcher = StandardTestDispatcher()


    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `zip code is correct`() {
        runTest {

            val query = "10033"

            whenever(response.city).thenReturn("New York")
            whenever(response.weatherData).thenReturn(listOf(data))

            whenever(getWeather(query)).thenReturn(
                flow()
                {
                    emit(Resource.Success(data = response))
                })

            viewModel.onSearch(query)
        }

        val expected = DataState(city = "New York", weatherData = listOf(data))
        val actual = viewModel.state.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `zip code is incorrect`() {
        runTest {

            val query = "100-0"

            whenever(getWeather(query)).thenReturn(
                flow()
                {
                    emit(Resource.Failure(data = null, massage = ERROR_ZIP))
                })

            viewModel.onSearch(query)
        }

        val expected = DataState(city = "", weatherData = emptyList(), massage = ERROR_ZIP)
        val actual = viewModel.state.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `zip code is correct no connection no cache`() {
        runTest {

            val query = "10033"


            whenever(getWeather(query)).thenReturn(
                flow()
                {
                    emit(Resource.Failure(data = null, massage = ERROR_CONNECTION))
                })

            viewModel.onSearch(query)
        }

        val expected = DataState(city = "", weatherData = emptyList(), massage = ERROR_CONNECTION)
        val actual = viewModel.state.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `zip code is correct no connection cache available`() {
        runTest {

            val query = "10033"
            val unixTime = System.currentTimeMillis() / 1000L

            whenever(response.city).thenReturn("New York")
            whenever(response.weatherData).thenReturn(listOf(data))

            whenever(data.dt).thenReturn(unixTime + 100)

            whenever(getWeather(query)).thenReturn(
                flow()
                {
                    emit(Resource.Failure(data = response, massage = ERROR_CONNECTION + ERROR_DATA))
                })

            viewModel.onSearch(query)
        }

        val expected = DataState(city = "New York", weatherData = listOf(data), massage = ERROR_CONNECTION + ERROR_DATA)
        val actual = viewModel.state.value

        Assertions.assertEquals(expected, actual)
    }

}
