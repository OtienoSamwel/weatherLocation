package com.otienosamwel.weatherlocation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.otienosamwel.weatherlocation.model.WeatherResponse
import com.otienosamwel.weatherlocation.ui.theme.WeatherLocationTheme
import com.otienosamwel.weatherlocation.viewmodel.MainViewModel
import com.otienosamwel.weatherlocation.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        WeatherViewModelFactory((application as WeatherApplication).weatherRepository)
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
            else -> {
                Toast.makeText(this, "You have to grant permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            WeatherLocationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Content(viewModel = viewModel)
                }
            }
        }

        getPermissionInterface()
        getLastKnowLocation()
    }

    private fun getLastKnowLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }


        fusedLocationClient.lastLocation.addOnSuccessListener {
            Log.i(TAG, "getLastKnowLocation: ${it.latitude} ${it.longitude}")
            viewModel.getWeatherForLocation(it.latitude.toString(), it.longitude.toString())
        }
    }

    private fun getPermissionInterface() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}

@Composable
fun Content(viewModel: MainViewModel) {
    val data = viewModel.locationData.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        data.value?.let {
            Elements(weatherResponse = it)
        }
    }
}


@Composable
fun Elements(weatherResponse: WeatherResponse) {
    Column() {
        weatherResponse.let {
            Text(text = "wind : ${it.wind}")
            Text(text = "pressure : ${it.main.pressure}")
            Text(text = "temp : ${it.main.temp}")
            Text(text = "humidity : ${it.main.humidity}")
        }
    }
}

