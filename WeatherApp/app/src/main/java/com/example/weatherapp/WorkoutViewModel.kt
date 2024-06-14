package com.example.weatherapp

import android.os.Build
import androidx.annotation.RequiresApi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date


class WorkoutViewModel : ViewModel() {

    val workoutDao = MainApplication.workoutDatabase.getWorkoutDao()
    val workoutList : LiveData<List<Workout>> = workoutDao.getAllWorkout()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addWorkout(title : String, description : String) {
        viewModelScope.launch ( Dispatchers.IO) {
            workoutDao.addWorkout(
                Workout(
                    title = title,
                    description = description,
                    createdAd = Date.from(Instant.now())
                )
            )
        }
    }

    fun deleteWorkout(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            workoutDao.deleteWorkout(id)
        }
    }
}