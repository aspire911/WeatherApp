package com.mdmx.weatherapp.presentation.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.mdmx.weatherapp.R
import com.mdmx.weatherapp.common.Constants.ICON_URL_PREFIX
import com.mdmx.weatherapp.common.Constants.ICON_URL_SUFFIX
import com.mdmx.weatherapp.domain.model.Data

@Composable
fun WeatherDataItem(
    weatherData: Data,
    imageLoader: ImageLoader,
    onItemClick: () -> Unit
) {
    Card(
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick() }
            ) {
            Row {

                val painter = rememberAsyncImagePainter(
                    ICON_URL_PREFIX + weatherData.weatherIcon + ICON_URL_SUFFIX,
                    imageLoader
                )
                Image(
                    painter = painter,
                    contentDescription = "icon",
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                )
                Column {
                    Text(text = weatherData.dateFormatted, fontWeight = FontWeight.Bold)
                    Text(
                        text = stringResource(R.string.weather_description),
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = weatherData.weatherDescription.uppercase(),
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = weatherData.temp.toInt().toString() + stringResource(R.string.f),
                    modifier = Modifier.fillMaxSize(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center
                )
            }
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
