package com.example.myweatherapp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myweatherapp.CacheHelper
import com.example.myweatherapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var weatherButton: Button
    private lateinit var latLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        weatherButton = findViewById(R.id.weatherButton)
        weatherButton.setOnClickListener { openWeather() }
    }

    private fun openWeather() {
        val mIntent = Intent(this, WeatherActivity::class.java)
        mIntent.putExtra("Latitude", latLng.latitude)
        mIntent.putExtra("Longitude", latLng.longitude)
        startActivity(mIntent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        latLng = CacheHelper.getCachedLocation(this)
        mMap = googleMap
        mMap.addMarker(MarkerOptions().position(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
        mMap.uiSettings.isMapToolbarEnabled = false
        googleMap.uiSettings.isZoomControlsEnabled = true

        googleMap.setOnMapClickListener {
            latLng = it
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).draggable(true))
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            CacheHelper.saveLocation(this, latLng)
        }

        if (checkGeoPermission()) {
            googleMap.isMyLocationEnabled = true
        }

    }

    private fun checkGeoPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }
}