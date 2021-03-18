package com.example.myweatherapp.apiUtils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkService {
    private val mRetrofit: Retrofit
    val weatherApi: WeatherApi


    init {
        val httpClient: OkHttpClient
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpBuilder = OkHttpClient.Builder()
        httpBuilder.addInterceptor(httpLoggingInterceptor).addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                    .addHeader("x-rapidapi-key", "d85395f85bmsh073dc91425510c0p1295c8jsnf9d220ea298b")
                    .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .build()
                return chain.proceed(request)
            }

        })
        httpClient = httpBuilder.build()
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://community-open-weather-map.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        weatherApi = mRetrofit.create(WeatherApi::class.java)

    }
}