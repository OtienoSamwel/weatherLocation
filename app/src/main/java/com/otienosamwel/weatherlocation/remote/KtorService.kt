package com.otienosamwel.weatherlocation.remote

import com.otienosamwel.weatherlocation.model.WeatherResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

object KtorService {

    private const val API_Key = "a968d87ad6d8bd7ff3fdf482e0939555"

    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    suspend fun getWeatherForLocation(latituide: String, longitude: String): WeatherResponse {
        return client.get("https://api.openweathermap.org/data/2.5/weather?lat=$latituide&lon=$longitude&appid=$API_Key")
    }
}