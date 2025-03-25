package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.darkSurface1
import com.example.weatherapp.ui.theme.darkSurface2
import com.example.weatherapp.ui.theme.lightSurface1
import com.example.weatherapp.ui.theme.lightSurface2

@Composable
fun ProfilePage(
    navController: NavHostController,
    iconsViewModel: IconsViewModel,
    darkThemeViewModel: DarkThemeViewModel
    ) {
    val isDark by darkThemeViewModel.isDark.observeAsState()
    Scaffold(
        topBar = {
            TopBar("Your Profile", navController = navController, darkThemeViewModel = darkThemeViewModel)
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                iconsViewModel = iconsViewModel,
                darkThemeViewModel = darkThemeViewModel
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
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
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "dumbell",
                modifier = Modifier
                    .size(200.dp)
                    .border(2.dp, color = Color.White, RoundedCornerShape(100))
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Jan Kowalski", fontSize = 50.sp, color = Color.White)
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.height(200.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.height(150.dp).width(300.dp),
                    text = "My name is Jan Kowalski and I love running and going to the gym. ",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

        }


    }
}

@Composable
@Preview
fun PreviewProfilePage() {
    ProfilePage(navController = rememberNavController(), iconsViewModel = IconsViewModel(), darkThemeViewModel = DarkThemeViewModel())
}