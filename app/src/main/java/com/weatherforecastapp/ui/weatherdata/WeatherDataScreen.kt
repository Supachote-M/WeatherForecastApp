/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weatherforecastapp.ui.weatherdata

import com.weatherforecastapp.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.weatherforecastapp.data.models.weatherforecast.WeatherForecastResponse

@Composable
fun WeatherDataScreen(modifier: Modifier = Modifier, viewModel: WeatherDataViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val forecastData = viewModel.forecastData.observeAsState()

    WeatherDataScreen(
        forecastData = forecastData,
        uiState = uiState,
        onClick = viewModel::getWeatherData,
    )
}

@Composable
internal fun WeatherDataScreen(
    forecastData: State<WeatherForecastResponse?>,
    uiState: WeatherForecastUiState,
    onClick: (name: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 24.dp, start = 12.dp, end = 12.dp, bottom = 24.dp)
    ) {
        var cityName by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                modifier = Modifier.weight(7f),
                value = cityName,
                onValueChange = { cityName = it }
            )

            Button(
                modifier = Modifier.weight(3f),
                onClick = { onClick(cityName) }) {
                Text("Forecast")
            }
        }
        when(uiState) {
            is WeatherForecastUiState.NoData -> {}
            is WeatherForecastUiState.Success -> {
                Text(
                    text = "Temperature : ${forecastData.value?.main?.temp}",
                    fontSize = 20.sp
                )
                Text(
                    text = "Humidity : ${forecastData.value?.main?.humidity}",
                    fontSize = 20.sp
                )
                Text(
                    text = "Description : ${forecastData.value?.weather?.first()?.description}",
                    fontSize = 20.sp
                )
            }
            is WeatherForecastUiState.Error -> {
                Text(
                    text = uiState.msg,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp
                )
            }
        }
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        WeatherDataScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        WeatherDataScreen()
    }
}
