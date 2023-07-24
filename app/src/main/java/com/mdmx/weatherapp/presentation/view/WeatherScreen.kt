package com.mdmx.weatherapp.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import com.mdmx.weatherapp.R
import com.mdmx.weatherapp.presentation.Screens
import com.mdmx.weatherapp.presentation.WeatherViewModel
import com.mdmx.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.mdmx.weatherapp.presentation.view.components.WeatherDataItem


@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    imageLoader: ImageLoader,
    navController: NavController
) {
    WeatherAppTheme {
        val state = viewModel.state.value
        Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                TextField(
                    value = viewModel.searchQuery.value,
                    onValueChange = viewModel::onSearch,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = stringResource(R.string.hint))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.massage.isNotBlank()) {
                    Text(
                        text = state.massage,
                        fontSize = 14.sp,
                        color = Color.Red
                    )
                }
                Text(
                    text = state.city,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Divider()
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.weatherData.size) { i ->
                        val weatherData = state.weatherData[i]
                        WeatherDataItem(
                            weatherData = weatherData,
                            imageLoader,
                            onItemClick = {
                                navController.navigate(Screens.Detail.route + "/${i}")
                            }
                        )
                    }
                }
            }
            if (state.isLoading)
                CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
        }
    }
}