package com.example.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
            val timerViewModel: TimerExampleViewModel by viewModels()
            val navController = rememberNavController()
            val iconsViewModel: IconsViewModel by viewModels()
            val workoutViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]
            val darkThemeViewModel: DarkThemeViewModel by viewModels()
            WeatherAppTheme {
                // A surface container using the  'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navig(
                        navController = navController,
                        weatherViewModel = weatherViewModel,
                        iconsViewModel = iconsViewModel,
                        timerViewModel = timerViewModel,
                        workoutViewModel = workoutViewModel,
                        darkThemeViewModel = darkThemeViewModel
                    )
                }
            }
        }
    }
}
