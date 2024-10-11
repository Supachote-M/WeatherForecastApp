package com.weatherforecastapp.data.models.weatherforecast

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherModel(
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String
) : Parcelable
