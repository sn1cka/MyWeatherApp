package com.example.myweatherapp.activities.moreWeather

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.apiUtils.NetworkService
import com.example.myweatherapp.models.MoreWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreActivity : AppCompatActivity() {

    private lateinit var weatherRecycler: RecyclerView
    private lateinit var retryView: Button
    private lateinit var progressView: ProgressBar

    private var adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        weatherRecycler = findViewById(R.id.weatherRecycler)
        progressView = findViewById(R.id.progressBar)
        retryView = findViewById(R.id.retryButton)

        retryView.setOnClickListener { getMoreWeatherForLocation() }
        weatherRecycler.adapter = adapter

        getMoreWeatherForLocation()
    }


    private fun showRetry() {
        weatherRecycler.isVisible = false
        progressView.isVisible = false
        retryView.isVisible = true
    }

    private fun getMoreWeatherForLocation() {
        retryView.isVisible = false
        progressView.isVisible = true
        NetworkService.weatherApi.getMoreWeather(
            intent.getDoubleExtra("Latitude", 0.0),
            intent.getDoubleExtra("Longitude", 0.0)
        ).enqueue(object : Callback<MoreWeather> {

            override fun onFailure(call: Call<MoreWeather>, t: Throwable) {
                showRetry()
                Toast.makeText(this@MoreActivity, "Проверьте ваше подключение к интернету", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<MoreWeather>, response: Response<MoreWeather>) {
                if (response.code() != 200) showRetry()
                else {
                    weatherRecycler.isVisible = true
                    progressView.isVisible = false
                    adapter.items = response.body()!!.list
                }
            }

        })
    }


}