package com.example.weatherapi

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lat=intent.getStringExtra("lat")
        val long=intent.getStringExtra("long")
        //Toast.makeText(this,lat+" "+long,Toast.LENGTH_LONG).show()
        if(lat != null && long != null) {
            getJsonData(lat,long);
        }
    }

    private fun getJsonData(lat:String,long:String){
        val API_KEY = "c75cd7cdd20bb7e33a9f4d743002e822"
        val queue = Volley.newRequestQueue(this)
        var cityName = getCityName(lat.toDouble(), long.toDouble());
        val url = "https://api.openweathermap.org/data/2.5/weather?q=${cityName}&appid=${API_KEY}"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                setValues(response)
            },
            Response.ErrorListener {
                Toast.makeText(
                    this,
                    "Please turn on internet connection",
                    Toast.LENGTH_LONG
                ).show()
            })


        queue.add(jsonRequest)
    }

    private fun getCityName(lat: Double, long: Double): String {
        var geoCoder = Geocoder(this, Locale.getDefault())
        var adress = geoCoder.getFromLocation(lat, long, 1)

        return adress.get(0).locality
    }

    private fun setValues(response: JSONObject) {
        city_id.text= "City name: " + response.getString("name");
        temp.text = "Temp: "+ response.getJSONObject("main").getString("temp")+" k"
        coordinate_id.text = response.getJSONObject("coord").getString("lat")+","+response.getJSONObject("coord").getString("lon")
    }
}