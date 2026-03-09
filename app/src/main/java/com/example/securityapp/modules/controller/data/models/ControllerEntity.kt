package com.example.securityapp.modules.controller.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "controller_entity")
data class ControllerEntity(
    @PrimaryKey(autoGenerate = false)
    val email : String,
    val number : String
)