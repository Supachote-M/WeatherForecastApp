package com.weatherforecastapp.data.models.weatherforecast

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("main")
    val main: ForecastModel,
    @SerializedName("weather")
    val weather: List<WeatherModel>
)