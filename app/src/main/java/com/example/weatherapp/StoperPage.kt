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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.weatherapp.ui.theme.darkSurface1
import com.example.weatherapp.ui.theme.darkSurface2
import com.example.weatherapp.ui.theme.lightSurface1
import com.example.weatherapp.ui.theme.lightSurface2
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit



@Composable
fun StoperPage(
    viewModel: TimerExampleViewModel,
    navController: NavHostController,
    iconsViewModel: IconsViewModel,
    darkThemeViewModel: DarkThemeViewModel
) {
    val isDark by darkThemeViewModel.isDark.observeAsState()
    Scaffold(
        topBar = {
            TopBar("Stoper", navController = navController, darkThemeViewModel = darkThemeViewModel)
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.linearGradient(
                        // use Brush.verticalGradeint to change the angle
                        colors = listOf(
                            if (isDark == false) lightSurface1 else darkSurface1, // Start color
                            if (isDark == false) lightSurface2 else darkSurface2 // End color
                        )
                    )
                )) {
                TimerExample(viewModel = viewModel, darkThemeViewModel = darkThemeViewModel)
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

class TimerExampleViewModel : ViewModel() {
    var time by mutableLongStateOf(0L)
    var isRunning by mutableStateOf(false)
    var startTime by mutableLongStateOf(0L)
    val intervalsList = mutableStateListOf<String>()
}



fun formatTime(timeMi: Long): String {
//    val hours = TimeUnit.MILLISECONDS.toHours(timeMi)
    val min = TimeUnit.MILLISECONDS.toMinutes(timeMi) % 60
    val sec = TimeUnit.MILLISECONDS.toSeconds(timeMi) % 60
    val millisec = (TimeUnit.MILLISECONDS.toMillis(timeMi) / 10) % 100
    return String.format("%02d:%02d,%02d", min, sec, millisec)
}

@Composable
fun TimerExample(
    viewModel: TimerExampleViewModel,
    darkThemeViewModel: DarkThemeViewModel
) {
    val isDark by darkThemeViewModel.isDark.observeAsState()
    val itemBackground =
        if (isDark == false)
            Color("#E2851D".toColorInt())
        else
            MaterialTheme.colorScheme.primary
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = formatTime(timeMi = viewModel.time),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(9.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 80.sp
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    shape = CircleShape,
                    onClick = {
                        if (viewModel.isRunning) {
                            viewModel.isRunning = false
                        } else {
                            viewModel.startTime = System.currentTimeMillis() - viewModel.time
                            viewModel.isRunning = true
                            keyboardController?.hide()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = itemBackground
                    ), modifier = Modifier.size(90.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(80.dp),
                        imageVector =
                        if (viewModel.isRunning)
                            Icons.Filled.Pause
                        else
                            Icons.Filled.PlayArrow,
                        contentDescription = ""
                    )

                }

                Button(
                    shape = CircleShape,
                    onClick = {

                        viewModel.isRunning = false
                        viewModel.intervalsList.add(formatTime(viewModel.time))
                        viewModel.time = 0
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = itemBackground
                    ),
                    modifier = Modifier.size(90.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(80.dp),
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = ""
                    )
                }

                Button(
                    shape = CircleShape,
                    onClick = {
                        viewModel.intervalsList.clear()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = itemBackground
                    ),
                    modifier = Modifier.size(90.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(80.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = ""
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            item {Divider(thickness = 1.dp)}
            viewModel.intervalsList.forEachIndexed{index, item ->
                item {
                    Column(modifier = Modifier.padding(vertical = 5.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Round ${index+1}",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontFamily = FontFamily.SansSerif
                            )
                            Text(
                                text = item,
                                fontSize = 22.sp,
                                color = Color.White,
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                        Divider(
                            thickness = 1.dp
                        )
                    }
                }



            }
        }


    }
    LaunchedEffect(viewModel.isRunning) {
        while (viewModel.isRunning) {
            delay(1)
            viewModel.time = System.currentTimeMillis() - viewModel.startTime
        }
    }
}

@Preview
@Composable
fun ShowStoper() {
    TimerExample(viewModel = TimerExampleViewModel(), darkThemeViewModel = DarkThemeViewModel())
}