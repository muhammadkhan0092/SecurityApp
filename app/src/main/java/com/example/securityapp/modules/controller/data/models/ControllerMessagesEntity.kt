package com.example.securityapp.modules.controller.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "controller_messages")
data class ControllerMessagesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val email : String,
    val message : String,
    val type : MessageFromControlled
)
