package com.example.securityapp.core.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.securityapp.core.domain.MessageTypeFromControlled

@Entity(tableName = "controller_messages")
data class MessagesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val email : String,
    val message : String,
    val type : MessageTypeFromControlled
)