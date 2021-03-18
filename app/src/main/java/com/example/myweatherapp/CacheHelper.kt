package com.example.myweatherapp

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson

object CacheHelper {
    private var gson = Gson()
    fun getCachedLocation(context: Context): LatLng {
        val preferences = context.getSharedPreferences("Location", Context.MODE_PRIVATE)
        return gson.fromJson(
            preferences.getString("LocationValue", gson.toJson(LatLng(42.82297851797549, 74.62132743813196))),
            LatLng::class.java
        )
    }

    fun saveLocation(context: Context, latLng: LatLng) {
        val editor = context.getSharedPreferences("Location", Context.MODE_PRIVATE).edit()
        editor.putString("LocationValue", gson.toJson(latLng))
        editor.apply()
    }
}