package com.example.myweatherapp.models

data class DetailedWeather(
    var cod: Int,
    var dt: Int,
    var main: MainInfo,
    var name: String?,
    var weather: List<Weather>,
    var wind: Wind,
    var dt_txt:String
)