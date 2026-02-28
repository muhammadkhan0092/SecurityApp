package com.example.securityapp.modules.controlled.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "controlled_entity")
data class ControlledEntity(
    @PrimaryKey(autoGenerate = false)
    val email : String,
    val numbers : List<String>
)