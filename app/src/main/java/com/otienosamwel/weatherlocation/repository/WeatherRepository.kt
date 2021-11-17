package com.otienosamwel.weatherlocation.repository

import com.otienosamwel.weatherlocation.local.WeatherDao
import com.otienosamwel.weatherlocation.remote.KtorService

class WeatherRepository(private val weatherDao: WeatherDao) {

    val weatherDbData = weatherDao.getWeather()

    suspend fun getWeather(latitude: String, longitude: String) {
        weatherDao.insertWeatherData(
            KtorService.getWeatherForLocation(
                latitude,
                longitude
            )
        )
    }
}