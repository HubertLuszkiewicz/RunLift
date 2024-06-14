package com.example.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.Converters
import com.example.weatherapp.Workout

@Database(entities = [Workout::class], version = 1)
@TypeConverters(Converters::class)
abstract class WorkoutDatabase : RoomDatabase() {

    companion object {
        const val NAME = "Workout_DB"
    }

    abstract fun getWorkoutDao() : WorkoutDao
}