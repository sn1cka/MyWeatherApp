package com.example.myweatherapp.apiUtils

import com.example.myweatherapp.models.DetailedWeather
import com.example.myweatherapp.models.MoreWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") lang:String = "ru",
        @Query("units") units:String = "metric"
    ): Call<DetailedWeather>

    @GET("forecast")
    fun getMoreWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") lang:String = "ru",
        @Query("units") units:String = "metric"
    ): Call<MoreWeather>

}
