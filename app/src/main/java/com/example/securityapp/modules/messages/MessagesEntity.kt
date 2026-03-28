package com.example.securityapp.modules.messages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "controller_messages")
data class MessagesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val email : String,
    val message : String,
    val type : MessageTypeFromControlled
)