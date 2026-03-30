package com.example.securityapp.modules.uninstall.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uninstall_entity")
data class UninstallEntity(
    @PrimaryKey(autoGenerate = false)
    val packageName : String
)