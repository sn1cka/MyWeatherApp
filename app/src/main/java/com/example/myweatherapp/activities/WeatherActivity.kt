package com.example.myweatherapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.myweatherapp.R
import com.example.myweatherapp.activities.moreWeather.MoreActivity
import com.example.myweatherapp.apiUtils.NetworkService
import com.example.myweatherapp.models.DetailedWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActivity : AppCompatActivity() {
    private lateinit var mainTemp: TextView
    private lateinit var feelsLike: TextView
    private lateinit var tempMinMax: TextView
    private lateinit var windSpeed: TextView

    private lateinit var weatherContainer:LinearLayout
    private lateinit var retryView:Button
    private lateinit var progressView:ProgressBar
    private lateinit var openMoreButton:Button

    private var longitude = 0.0
    private  var latitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        mainTemp = findViewById(R.id.weatherTemp)
        feelsLike = findViewById(R.id.feelsLike)
        tempMinMax = findViewById(R.id.minMaxTemp)
        windSpeed = findViewById(R.id.windSpeed)

        progressView = findViewById(R.id.progressBar)
        weatherContainer = findViewById(R.id.weatherContainer)
        retryView = findViewById(R.id.retryButton)
        openMoreButton = findViewById(R.id.openMore)


        openMoreButton.setOnClickListener { openMore() }
        retryView.setOnClickListener { getWeather() }

        latitude  = intent.getDoubleExtra("Latitude", 0.0)
        longitude = intent.getDoubleExtra("Longitude", 0.0)

        getWeather()
    }

    private fun openMore(){
        val mIntent = Intent(this, MoreActivity::class.java)
        mIntent.putExtra("Longitude", longitude)
        mIntent.putExtra("Latitude", latitude)
        startActivity(mIntent)
    }

    private fun getWeather() {
        progressView.isVisible = true
        weatherContainer.isVisible = false
        retryView.isVisible = false

        NetworkService.weatherApi.getWeather(
            latitude, longitude
        ).enqueue(object : Callback<DetailedWeather> {
            override fun onFailure(call: Call<DetailedWeather>, t: Throwable) {
                showRetry()
                Toast.makeText(this@WeatherActivity, "Проверьте ваше подключение к инетренту", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DetailedWeather>, response: Response<DetailedWeather>) {
                if (response.code() == 200) setViewData(response.body()!!)
                else showRetry()
            }

        })
    }

    fun showRetry(){
        progressView.isVisible = false
        weatherContainer.isVisible = false
        retryView.isVisible = true
    }



    @SuppressLint("SetTextI18n")
    fun setViewData(data: DetailedWeather) {
        supportActionBar!!.title = data.name
        mainTemp.text = "Температура: ${data.main.temp}°С, ${data.weather[0].description}"
        feelsLike.text = "Ощущается как: ${data.main.feels_like}°С"
        tempMinMax.text = "Mакс/Мин температура: ${data.main.temp_max}/${data.main.temp_min}°С"
        windSpeed.text = "Скорость ветра: ${data.wind.speed} м/с"
        weatherContainer.isVisible = true
        progressView.isVisible = false

    }
}