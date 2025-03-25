package com.example.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.weatherapp.api.Condition
import com.example.weatherapp.api.Current
import com.example.weatherapp.api.Location
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.WeatherModel
import com.example.weatherapp.ui.theme.darkSurface1
import com.example.weatherapp.ui.theme.darkSurface2
import com.example.weatherapp.ui.theme.lightSurface1
import com.example.weatherapp.ui.theme.lightSurface2

@Composable
fun WeatherPage(
    viewModel: WeatherViewModel,
    navController: NavHostController,
    iconsViewModel: IconsViewModel,
    darkThemeViewModel: DarkThemeViewModel
) {
    val isDark by darkThemeViewModel.isDark.observeAsState()
    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()

    Scaffold(
        topBar = {
            TopBar("Weather", navController = navController, darkThemeViewModel = darkThemeViewModel)
        },
        content = {
            paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        brush = Brush.linearGradient(
                            // use Brush.verticalGradeint to change the angle
                            colors = listOf(
                                if (isDark==false) lightSurface1 else darkSurface1, // Start color
                                if (isDark==false) lightSurface2 else darkSurface2 // End color
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            focusedTextColor = Color.White,
                            focusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,

                            ),
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.getData(city) }
                            ) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search for any location",
                                    tint = Color.White
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        value = city,
                        onValueChange = {
                            city = it
                        },
                        label = {
                            Text(text = "Search for any location")
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                when(val result = weatherResult.value) {
                    is NetworkResponse.Error -> {
                        Text(
                            text = result.message,
                            color = Color.White
                        )
                    }
                    NetworkResponse.Loading -> {
                        CircularProgressIndicator(color = Color.White)
                    }
                    is NetworkResponse.Success -> {
                        WeatherDetails(data = result.data)
                    }
                    null -> {}
                }
            }
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                iconsViewModel = iconsViewModel,
                darkThemeViewModel = darkThemeViewModel
            )
        }
    )

}

@Composable
fun WeatherDetails(data : WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = " ${data.location.name}, ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = data.location.country,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current.condition.icon}".replace("64x64","128x128"),
            contentDescription = "Condition icon"
        )

        Text(
            text = " ${stringToInt(data.current.temp_c)}°",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(100.dp)
            ) {
                Icon(imageVector = Icons.Default.Air, contentDescription ="", tint = Color.White)
                Text(text = stringToInt(data.current.wind_kph), color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "Wind", color = Color.White)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(100.dp)
            ) {
                Icon(imageVector = Icons.Default.WaterDrop, contentDescription = "", tint = Color.White)
                Text(text = data.current.humidity, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "Humidity", color = Color.White)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier

                    .width(100.dp)
            ) {
                Icon(imageVector = Icons.Default.Thermostat, contentDescription = "", tint = Color.White)
                Text(text = stringToInt(data.current.pressure_mb), color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "Pressure", color = Color.White)
            }
        }

    }
}

fun stringToInt(doubleString: String): String {
    val number = doubleString.toDoubleOrNull()
    if (number != null) {
        return number.toInt().toString()
    }
    return doubleString
}
val weatherModel = WeatherModel(
    current = Current(
        cloud = "75",
        condition = Condition(
            text = "Partly cloudy",
            icon = "//cdn.weatherapi.com/weather/64x64/day/116.png",
            code = "1003"
        ),
        dewpoString_c = "5.8",
        dewpoString_f = "42.4",
        feelslike_c = "14.4",
        feelslike_f = "57.9",
        gust_kph = "15.3",
        gust_mph = "9.5",
        heatindex_c = "14.9",
        heatindex_f = "58.9",
        humidity = "51",
        is_day = "1",
        last_updated = "2024-06-12 15:00",
        last_updated_epoch = "1718200800",
        precip_in = "0.0",
        precip_mm = "0.02",
        pressure_in = "30.12",
        pressure_mb = "1020.0",
        temp_c = "15.0",
        temp_f = "59.0",
        uv = "3.0",
        vis_km = "10.0",
        vis_miles = "6.0",
        wind_degree = "290",
        wind_dir = "WNW",
        wind_kph = "13.0",
        wind_mph = "8.1",
        windchill_c = "14.3",
        windchill_f = "57.7"
    ),
    location = Location(
        country = "United Kingdom",
        lat = "51.52",
        localtime = "2024-06-12 15:08",
        localtime_epoch = "1718201317",
        lon = "-0.11",
        name = "London",
        region = "City of London, Greater London",
        tz_id = "Europe/London"
    )
)


@Preview
@Composable
fun WeatherDetailsPreview() {
    WeatherDetails(data = weatherModel)
}

