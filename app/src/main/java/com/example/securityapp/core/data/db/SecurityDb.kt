package com.example.securityapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.securityapp.modules.controlled.data.ControlledDao
import com.example.securityapp.modules.controlled.data.ControlledEntity
import com.example.securityapp.modules.controller.data.dao.ControllerDao
import com.example.securityapp.modules.controller.data.models.ControllerEntity

@Database(entities = [ControlledEntity::class, ControllerEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class SecurityDb : RoomDatabase(){
    abstract fun controllerDao() : ControllerDao
    abstract fun controlledDao() : ControlledDao
}