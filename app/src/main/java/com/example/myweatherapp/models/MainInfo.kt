package com.example.myweatherapp.models

data class MainInfo(
    var feels_like: Double,
    var temp: Double,
    var temp_max: Double,
    var temp_min: Double,
    var dt:Int? = null
)