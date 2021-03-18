package com.example.myweatherapp.activities.moreWeather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.models.DetailedWeather

class Adapter : RecyclerView.Adapter<WeatherViewHolder>() {
    var items: List<DetailedWeather> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }



    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class WeatherViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private var date:TextView = view.findViewById(R.id.date)
    private var temp:TextView = view.findViewById(R.id.temp)
    @SuppressLint("SetTextI18n")
    fun bind(item: DetailedWeather) {
        date.text = item.dt_txt
        temp.text = "${item.main.temp}°С, ${item.weather[0].description}"
    }

}
