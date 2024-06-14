package com.example.weatherapp

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.db.WorkoutDatabase

class MainApplication : Application(){
    companion object {
        lateinit var workoutDatabase : WorkoutDatabase
    }

    override fun onCreate() {
        super.onCreate()
        workoutDatabase = Room.databaseBuilder(
            applicationContext,
            WorkoutDatabase::class.java,
            WorkoutDatabase.NAME
        ).build()
    }
}