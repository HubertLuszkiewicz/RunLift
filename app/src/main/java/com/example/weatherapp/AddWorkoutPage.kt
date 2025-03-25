package com.example.weatherapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.darkSurface1
import com.example.weatherapp.ui.theme.darkSurface2
import com.example.weatherapp.ui.theme.lightSurface1
import com.example.weatherapp.ui.theme.lightSurface2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutPage(navController: NavController, workoutViewModel: WorkoutViewModel, darkThemeViewModel: DarkThemeViewModel) {
    var title by remember { mutableStateOf("")}
    var description by remember { mutableStateOf("")}
    var isClicked by remember { mutableStateOf(false)}
    val isDark by darkThemeViewModel.isDark.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val eraseData: () -> Unit = {
        coroutineScope.launch {
            delay(1000)
            title = ""
            description = ""
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    actionIconContentColor =
                    MaterialTheme.colorScheme.primary,
                    navigationIconContentColor =
                    MaterialTheme.colorScheme.primary,
                    containerColor =
                    if (isDark == false)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        Color.Black
                    ,
                    titleContentColor =
                    MaterialTheme.colorScheme.primary
                ),
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Add Workout")
                    }

                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        eraseData()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Navigation Icon")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        darkThemeViewModel.updateIsDark()
                    }) {
                        Icon(
                            if (isDark == false)
                                Icons.Filled.DarkMode
                            else
                                Icons.Filled.LightMode
                            , contentDescription = "Action Icon"
                        )
                    }
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(brush = Brush.linearGradient(
                    // use Brush.verticalGradient to change the angle
                    colors = listOf(
                        if (isDark==false) lightSurface1 else darkSurface1, // Start color
                        if (isDark==false) lightSurface2 else darkSurface2 // End color
                    )
                ))
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column {
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        ),
                    placeholder = { Text(text = "Eg. Evening workout with friends", color = Color.White)},
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .padding(top = 8.dp)
                )
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedTextColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,

                        ),
                    placeholder = { Text(text = "Deadlift 5x5x100\nBench press 4x8x70\nTreadmill 3x3 km @4:00/km", color = Color.White) },
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = title.isNotEmpty(),
                onClick = {
                    workoutViewModel.addWorkout(title,description)
                    navController.popBackStack()
                    isClicked = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (!isClicked) Color("#E2851D".toColorInt()) else Color("#C9720F".toColorInt()),
                    disabledContainerColor = Color.DarkGray
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)

            ) {
                Text(
                    "Add Workout",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 15.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun AddWorkoutPagePreview() {
    AddWorkoutPage(navController = rememberNavController(), workoutViewModel = WorkoutViewModel(), darkThemeViewModel = DarkThemeViewModel())
}