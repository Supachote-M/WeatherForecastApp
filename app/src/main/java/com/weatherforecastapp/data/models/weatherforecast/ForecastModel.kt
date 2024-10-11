package com.weatherforecastapp.data.models.weatherforecast

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastModel(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("humidity")
    val humidity: Double
) : Parcelable
