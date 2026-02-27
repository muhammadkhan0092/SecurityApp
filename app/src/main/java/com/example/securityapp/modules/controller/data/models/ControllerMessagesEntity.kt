package com.example.securityapp.modules.controller.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.core.domain.MessageTypeFromControlled


@Entity(tableName = "controller_messages")
data class ControllerMessagesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val email : String,
    val message : String,
    val type : MessageTypeFromControlled
)
