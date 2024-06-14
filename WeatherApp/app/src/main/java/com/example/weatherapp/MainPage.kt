package com.example.weatherapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.weatherapp.ui.theme.darkSurface1
import com.example.weatherapp.ui.theme.darkSurface2
import com.example.weatherapp.ui.theme.lightSurface1
import com.example.weatherapp.ui.theme.lightSurface2
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(
    navController : NavHostController,
    iconsViewModel: IconsViewModel,
    workoutViewModel: WorkoutViewModel,
    darkThemeViewModel: DarkThemeViewModel
) {
    val isDark by darkThemeViewModel.isDark.observeAsState()
    val workoutList = workoutViewModel.workoutList.observeAsState(initial = emptyList())
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopBar("Home", navController = navController, darkThemeViewModel = darkThemeViewModel)
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor =
                if (isDark == false)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    Color.Black,
                contentColor =
                    MaterialTheme.colorScheme.primary,
                onClick = {navController.navigate("AddWorkoutPage")}
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        bottomBar = {BottomBar(
            navController = navController,
            iconsViewModel = iconsViewModel,
            darkThemeViewModel = darkThemeViewModel
        )}
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(
                    brush = Brush.linearGradient(
                        // use Brush.verticalGradeint to change the angle
                        colors = listOf(
                            if (isDark==false) lightSurface1 else darkSurface1, // Start color
                            if (isDark==false) lightSurface2 else darkSurface2 // End color
                        )
                    )
                )
                .fillMaxSize()
        ) {
            itemsIndexed(
                items = workoutList.value,
                key = { index, item -> item.hashCode() }, // Example of generating a key based on item content
                itemContent = { index:Int, item :Workout->
                    // Your composable for each item
                    Item(item = item, onDelete = {workoutViewModel.deleteWorkout(item.id)}, darkThemeViewModel = darkThemeViewModel)
                }
            )
        }
    }
}


//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun MainPagePreview() {
//    MainPage(
//        navController = rememberNavController(),
//        iconsViewModel = IconsViewModel(),
//        workoutViewModel = WorkoutViewModel()
//    )
//}
//
val workout = Workout(0, "Evening Weight Training with fellas",
    "3 sets of dips\n3x8 barbell row\nelo\n320\nsfssdf\n4234", Date()
)

@Composable
fun Item(item: Workout, onDelete: () -> Unit, darkThemeViewModel: DarkThemeViewModel) {
    val isDark by darkThemeViewModel.isDark.observeAsState()
    val itemBackground =
        if (isDark == false)
            Color("#E2851D".toColorInt())
        else
            MaterialTheme.colorScheme.primary
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(itemBackground)
            .padding(vertical = 16.dp)
            .padding(start = 16.dp)) {
        Column(modifier = Modifier.weight(5f)) {
            Text(text = item.title, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Text(item.description, fontSize = 15.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Divider(
            modifier = Modifier
                .fillMaxHeight()  //fill the max height
                .width(1.dp)
            , color = Color.White)
        IconButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            onClick = { onDelete() }
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun ItemPreview() {
    Item(workout, {}, darkThemeViewModel = DarkThemeViewModel())
}

class IconsViewModel : ViewModel() {
    private val _selectedItemIndex = MutableLiveData(0)
    val selectedItemIndex: LiveData<Int> = _selectedItemIndex

    fun updateSelectedItemIndex(value : Int) {
        _selectedItemIndex.value = value
    }
}