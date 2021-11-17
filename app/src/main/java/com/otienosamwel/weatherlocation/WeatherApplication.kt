package com.otienosamwel.weatherlocation

import android.app.Application
import com.otienosamwel.weatherlocation.local.WeatherDataBase
import com.otienosamwel.weatherlocation.repository.WeatherRepository

class WeatherApplication : Application() {
    private val db by lazy { WeatherDataBase.getDatabase(this) }
    val weatherRepository by lazy { WeatherRepository(db.weatherDao()) }
}