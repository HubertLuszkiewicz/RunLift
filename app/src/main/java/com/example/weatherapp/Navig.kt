package com.example.weatherapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navig(
    weatherViewModel: WeatherViewModel,
    navController: NavHostController,
    iconsViewModel: IconsViewModel,
    timerViewModel: TimerExampleViewModel,
    workoutViewModel: WorkoutViewModel,
    darkThemeViewModel: DarkThemeViewModel
){
    NavHost(
        navController = navController,
        startDestination = "StartAnimation",
        enterTransition = {
            fadeIn(animationSpec = tween(1000)) +
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        },
        exitTransition = {
            fadeOut(animationSpec = tween(1000)) +
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        },
        popEnterTransition = { fadeIn(animationSpec = tween(1000))},
        popExitTransition = { fadeOut(animationSpec = tween(1000))}
    ){
        composable(
            route = "StartAnimation"
        ){
            StartAnimation(navController = navController)
        }
        composable(
            route = "Home"
        ){
            MainPage(
                navController = navController,
                iconsViewModel = iconsViewModel,
                workoutViewModel = workoutViewModel,
                darkThemeViewModel = darkThemeViewModel
            )
        }
        composable(route = "Weather"){
            WeatherPage(
                viewModel = weatherViewModel,
                navController = navController,
                iconsViewModel = iconsViewModel,
                darkThemeViewModel = darkThemeViewModel
            )
        }
        composable(route = "Stoper"){
            StoperPage(
                viewModel = timerViewModel,
                navController = navController,
                iconsViewModel = iconsViewModel,
                darkThemeViewModel = darkThemeViewModel
            )
        }
        composable(route = "AddWorkoutPage"){
            AddWorkoutPage(
                navController = navController,
                workoutViewModel = workoutViewModel,
                darkThemeViewModel = darkThemeViewModel
            )
        }
        composable(
            route = "ProfilePage"
        ){
            ProfilePage(
                navController = navController,
                iconsViewModel = iconsViewModel,
                darkThemeViewModel = darkThemeViewModel
            )
        }
    }
}