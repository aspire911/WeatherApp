package com.mdmx.weatherapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdmx.weatherapp.common.Constants.UNKNOWN_ERROR
import com.mdmx.weatherapp.common.Constants.ZIP_CODE_LENGTH
import com.mdmx.weatherapp.common.Resource
import com.mdmx.weatherapp.domain.use_case.GetWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeather: GetWeather
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(DataState())
    val state: State<DataState> = _state

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        if (query.length > ZIP_CODE_LENGTH) return
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L) // delay before executing query
            getWeather(query)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                city = result.data?.city.orEmpty(),
                                weatherData = result.data?.weatherData.orEmpty(),
                                isLoading = false
                            )
                        }
                        is Resource.Failure -> {
                            val unixTime = System.currentTimeMillis() / 1000L
                            val actualData = result.data?.weatherData?.filter { it.dt > unixTime }
                            _state.value = state.value.copy(
                                city = result.data?.city.orEmpty(),
                                weatherData = actualData.orEmpty(),
                                massage = result.massage ?: UNKNOWN_ERROR,
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                massage = "",
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }
}