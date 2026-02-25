package com.example.securityapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.securityapp.core.data.controlled.ControlledDao
import com.example.securityapp.core.data.controlled.ControlledEntity
import com.example.securityapp.core.data.controller.ControllerDao
import com.example.securityapp.core.data.controller.ControllerEntity

@Database(entities = [ControlledEntity::class, ControllerEntity::class], version = 1)
abstract class SecurityDb : RoomDatabase(){
    abstract fun controllerDao() : ControllerDao
    abstract fun controlledDao() : ControlledDao
}