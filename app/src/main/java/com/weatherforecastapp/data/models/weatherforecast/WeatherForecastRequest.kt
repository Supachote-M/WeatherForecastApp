package com.weatherforecastapp.data.models.weatherforecast

import com.google.gson.annotations.SerializedName

data class WeatherForecastRequest(
  @SerializedName("q")
  val q: String,
  @SerializedName("APPID")
  val APPID: String,
  @SerializedName("units")
  val units: String
)
