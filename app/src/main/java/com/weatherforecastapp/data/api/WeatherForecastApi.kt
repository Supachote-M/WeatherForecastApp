package com.weatherforecastapp.data.api

import com.weatherforecastapp.data.models.weatherforecast.WeatherForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApi {

    @GET("data/2.5/weather")
    fun getCurrentWeatherForecast(
        @Query("APPID") appID: String,
        @Query("q") q: String,
        @Query("units") units: String
    ): Call<WeatherForecastResponse>

}
