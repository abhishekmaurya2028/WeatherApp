package com.example.myapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SearchView
import com.example.myapplication.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// key   4f19227a93b64f977beacd848a451bd8
//api.openweathermap.org/data/2.5/weather?q=lucknow&appid=4f19227a93b64f977beacd848a451bd8
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchWeatherData("Lucknow")
        SearchCity()

    }

    private fun SearchCity() {
        val searchView=binding.searchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    private fun fetchWeatherData(CityName:String) {
        //1:01
        val url="https://api.openweathermap.org/data/2.5/";
        val retrofit=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build().create(ApiInterface::class.java)

        val response=retrofit.getWeatherData(CityName,"4f19227a93b64f977beacd848a451bd8", "matric")

        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>?, response: Response<WeatherApp>?) {
                val responseBody= response?.body()
                if (response != null) {
                    if(response.isSuccessful && responseBody!=null){
                        val temperature=responseBody.main.temp.toString()
                        val humidity=responseBody.main.humidity
                        val windSpeed=responseBody.wind.speed
                        val sunRise=responseBody.sys.sunrise.toLong()
                        val sunSet=responseBody.sys.sunset.toLong()
                        val seaLevel=responseBody.main.pressure
                        val condition=responseBody.weather.firstOrNull()?.main?:"unknown"
                        val maxTemp=responseBody.main.temp_max
                        val minTemp=responseBody.main.temp_min

                        binding.temperature.text="$temperature ℃"
                        binding.weather.text="$condition"
                        binding.maxTemp.text="$maxTemp ℃"
                        binding.minTemp.text="$minTemp ℃"
//                        binding.humadity.
                        binding.windSpeed.text="$windSpeed m/s"
                        binding.sunrise.text="${time(sunRise)}"
                        binding.sunset.text="${sunSet}"
                        binding.sea.text="$seaLevel"
                        binding.humidity.text="$humidity"
                        binding.condition.text=condition
                        binding.day.text=dayName(System.currentTimeMillis())
                        binding.date.text= date()
                        binding.cityName.text="$CityName"

//                        Log.d("abhi", "onResponse: $temperature")
                        changeImagesAccordingToWeatherCondtion(condition)

                    }

                }
            }



            override fun onFailure(call: Call<WeatherApp>?, t: Throwable?) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun changeImagesAccordingToWeatherCondtion(conditons:String) {
        when(conditons){
            "Mist" ->{
                //change this image because this is darek
                binding.root.setBackgroundResource(R.drawable.cloudback)
//                1:28
            }
            else ->{
                binding.root.setBackgroundResource(R.drawable.mainback)
            }
        }
    }

    private fun date(): CharSequence? {
        val sdf=SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())

    }
    private fun time(timestap:Long): String {
        val sdf=SimpleDateFormat("HH mm", Locale.getDefault())
        return sdf.format(Date(timestap*1000))

    }
    fun dayName(timestap:Long):String{
        val sdf=SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }

}