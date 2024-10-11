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

package com.weatherforecastapp.data

import com.weatherforecastapp.data.api.WeatherForecastApi
import com.weatherforecastapp.data.models.weatherforecast.WeatherForecastRequest
import com.weatherforecastapp.data.models.weatherforecast.WeatherForecastResponse
import retrofit2.Call
import javax.inject.Inject

interface WeatherDataRepository {
    fun getWeatherDataFromCity(request: WeatherForecastRequest): Call<WeatherForecastResponse>
}

class DefaultWeatherDataRepository @Inject constructor(
    private val weatherForecastApi: WeatherForecastApi
) : WeatherDataRepository {

    override fun getWeatherDataFromCity(request: WeatherForecastRequest): Call<WeatherForecastResponse> {
        return weatherForecastApi.getCurrentWeatherForecast(request.APPID, request.q, request.units)
    }
}
