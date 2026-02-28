package com.example.securityapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.securityapp.modules.controlled.data.source.ControlledDao
import com.example.securityapp.modules.controlled.data.models.ControlledEntity
import com.example.securityapp.modules.controller.data.dao.ControllerDao
import com.example.securityapp.core.data.dao.ControllerMessagesDao
import com.example.securityapp.modules.controller.data.models.ControllerEntity
import com.example.securityapp.core.data.models.MessagesEntity

@Database(entities = [ControlledEntity::class, ControllerEntity::class, MessagesEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class SecurityDb : RoomDatabase(){
    abstract fun controllerDao() : ControllerDao
    abstract fun controlledDao() : ControlledDao
    abstract fun controllerMessagesDao() : ControllerMessagesDao
}