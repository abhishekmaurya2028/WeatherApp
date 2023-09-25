package com.example.myapplication

import com.example.myapplication.Clouds
import com.example.myapplication.Coord
import com.example.myapplication.Main
import com.example.myapplication.Sys
import com.example.myapplication.Weather

data class WeatherApp(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)