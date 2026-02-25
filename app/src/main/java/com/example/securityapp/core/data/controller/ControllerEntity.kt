package com.example.securityapp.core.data.controller

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "controller_entity")
data class ControllerEntity(
    @PrimaryKey(autoGenerate = false)
    val email : String,
    val numbers : List<String>
)
