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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherforecastapp.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.weatherforecastapp.data.WeatherDataRepository
import com.weatherforecastapp.data.models.weatherforecast.WeatherForecastRequest
import com.weatherforecastapp.data.models.weatherforecast.WeatherForecastResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherDataViewModel @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) : ViewModel() {

    private val _forecastData = MutableLiveData<WeatherForecastResponse>()
    val forecastData: LiveData<WeatherForecastResponse>
        get() = _forecastData

    private val _uiState = MutableStateFlow<WeatherForecastUiState>(WeatherForecastUiState.NoData)
    val uiState: StateFlow<WeatherForecastUiState> = _uiState.asStateFlow()

    fun getWeatherData(name: String) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            when (throwable) {
                is IOException -> _uiState.value = WeatherForecastUiState.Error("NETWORK ERROR")
                else -> {
                    _uiState.value = WeatherForecastUiState.Error("OTHER ERROR")
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO + handler) {
            val result = weatherDataRepository.getWeatherDataFromCity(WeatherForecastRequest(
                q = name,
                units = "standard",
                APPID = BuildConfig.API_KEY
            )).execute()
            if(result.isSuccessful) {
                _forecastData.postValue(result.body())
                _uiState.value = WeatherForecastUiState.Success
            } else {
                result.code()
                _uiState.value = WeatherForecastUiState.Error("No result from city name input")
            }
        }
    }
}

sealed interface WeatherForecastUiState {
    object NoData : WeatherForecastUiState
    object Success : WeatherForecastUiState
    data class Error(val msg: String) : WeatherForecastUiState
}
