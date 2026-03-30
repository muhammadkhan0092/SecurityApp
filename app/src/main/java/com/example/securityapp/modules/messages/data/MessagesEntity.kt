package com.example.securityapp.modules.messages.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.securityapp.modules.messages.domain.MessageTypeFromControlled

@Entity(tableName = "controller_messages")
data class MessagesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val email : String,
    val message : String,
    val type : MessageTypeFromControlled
)