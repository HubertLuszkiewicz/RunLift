package com.example.weatherapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var description: String,
    var createdAd: Date
)
