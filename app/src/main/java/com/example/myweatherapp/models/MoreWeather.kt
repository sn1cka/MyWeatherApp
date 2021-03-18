package com.example.myweatherapp.models

data class MoreWeather(
    var cnt: Int,
    var cod: String,
    var list: List<DetailedWeather>,
    var message: Int
)
