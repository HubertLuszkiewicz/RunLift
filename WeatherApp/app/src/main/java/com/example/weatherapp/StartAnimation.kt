package com.example.weatherapp

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun StartAnimation(
    navController: NavHostController
) {
    var show by remember { mutableStateOf(true) }
    var isRotated by rememberSaveable {
        mutableStateOf(false)
    }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 3600F else 0f,
        animationSpec = tween(durationMillis = 5000)
    )
    LaunchedEffect(key1 = Unit){
        isRotated = true
        delay(5000)
        show = false
    }
    Column(modifier= Modifier.fillMaxSize()) {
        if(show){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hantla),
                    contentDescription = "dumbell",
                    modifier = Modifier
                        .rotate(rotationAngle)
                        .size(300.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = painterResource(id = R.drawable.runlift),
                    contentDescription = "runLiftLogo",
                    modifier = Modifier

                        .height(200.dp)
                )
//                Text(
//                    text = "Run & Lift",
//                    fontSize = 40.sp
//                )
            }

        } else {
            navController.navigate("Home")
        }
    }
}

