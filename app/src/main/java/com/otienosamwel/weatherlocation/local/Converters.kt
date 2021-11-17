package com.otienosamwel.weatherlocation.local

import androidx.room.TypeConverter
import com.otienosamwel.weatherlocation.model.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun cloudsToJson(clouds: Clouds): String = Json.encodeToString(clouds)

    @TypeConverter
    fun cloudsFromJson(json: String): Clouds = Json.decodeFromString(json)

    @TypeConverter
    fun coordToJson(coord: Coord): String = Json.encodeToString(coord)

    @TypeConverter
    fun coordFromJson(json: String): Coord = Json.decodeFromString(json)

    @TypeConverter
    fun mainToJson(main: Main): String = Json.encodeToString(main)

    @TypeConverter
    fun mainFromJson(json: String): Main = Json.decodeFromString(json)

    @TypeConverter
    fun sysToJson(sys: Sys): String = Json.encodeToString(sys)

    @TypeConverter
    fun sysFromJson(json: String): Sys = Json.decodeFromString(json)

    @TypeConverter
    fun windToJson(wind: Wind): String = Json.encodeToString(wind)

    @TypeConverter
    fun windFromJson(json: String): Wind = Json.decodeFromString(json)

    @TypeConverter
    fun weatherToJson(weather: List<Weather>): String = Json.encodeToString(weather)

    @TypeConverter
    fun weatherFromJson(json: String): List<Weather> = Json.decodeFromString(json)

}