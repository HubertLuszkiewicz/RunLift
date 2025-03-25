package com.example.weatherapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.Workout

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM WORKOUT")
    fun getAllWorkout() : LiveData<List<Workout>>

    @Insert
    fun addWorkout(workout : Workout)

    @Query("Delete FROM WORKOUT where id = :id")
    fun deleteWorkout(id : Int)
}