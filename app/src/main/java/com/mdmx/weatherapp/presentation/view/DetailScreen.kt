package com.mdmx.weatherapp.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.mdmx.weatherapp.R
import com.mdmx.weatherapp.common.Constants
import com.mdmx.weatherapp.presentation.WeatherViewModel
import com.mdmx.weatherapp.presentation.view.components.WeatherDataDisplay
import java.util.Locale

@Composable
fun DetailScreen(
    index: String?,
    viewModel: WeatherViewModel,
    imageLoader: ImageLoader
) {
    if (viewModel.state.value.weatherData.isNotEmpty()) {
        index?.let { i ->
            val weatherData = viewModel.state.value.weatherData[i.toInt()]
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    backgroundColor = Color.LightGray,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val sdf = java.text.SimpleDateFormat("MM-dd-yyyy - hh:mm a", Locale.getDefault())
                        val date = java.util.Date(weatherData.dt * 1000L)

                        Text(
                            text = sdf.format(date),
                            modifier = Modifier.align(Alignment.End)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        val painter = rememberAsyncImagePainter(
                            Constants.ICON_URL_PREFIX + weatherData.weatherIcon + Constants.ICON_URL_SUFFIX,
                            imageLoader
                        )
                        Image(
                            painter = painter,
                            contentDescription = "icon",
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = weatherData.temp.toInt()
                                .toString() + stringResource(R.string.f),
                            fontSize = 50.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = weatherData.weatherDescription.uppercase(),
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherDataDisplay(
                                value = weatherData.pressure,
                                unit = stringResource(id = R.string.hpa),
                                icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                                iconTint = Color.Black
                            )
                            WeatherDataDisplay(
                                value = weatherData.humidity,
                                unit = stringResource(R.string.percent),
                                icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                                iconTint = Color.Black
                            )
                            WeatherDataDisplay(
                                value = weatherData.windSpeed.toInt(),
                                unit = stringResource(R.string.mph),
                                icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                                iconTint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}