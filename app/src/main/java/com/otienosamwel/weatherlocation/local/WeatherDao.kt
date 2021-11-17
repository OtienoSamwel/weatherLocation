package com.otienosamwel.weatherlocation.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otienosamwel.weatherlocation.model.WeatherResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_data")
    fun getWeather(): Flow<WeatherResponse>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeatherData(weatherData: WeatherResponse)

}