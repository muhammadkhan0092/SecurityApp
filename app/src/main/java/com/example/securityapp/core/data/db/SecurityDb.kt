package com.example.securityapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.securityapp.modules.controlled.data.source.ControlledDao
import com.example.securityapp.modules.controlled.data.models.ControlledEntity
import com.example.securityapp.modules.controller.data.dao.ControllerDao
import com.example.securityapp.modules.messages.data.ControllerMessagesDao
import com.example.securityapp.modules.uninstall.data.UninstallDao
import com.example.securityapp.modules.controller.data.models.ControllerEntity
import com.example.securityapp.modules.messages.data.MessagesEntity
import com.example.securityapp.modules.uninstall.data.UninstallEntity

@Database(entities = [ControlledEntity::class, ControllerEntity::class, MessagesEntity::class, UninstallEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class SecurityDb : RoomDatabase(){
    abstract fun controllerDao() : ControllerDao
    abstract fun controlledDao() : ControlledDao
    abstract fun controllerMessagesDao() : ControllerMessagesDao
    abstract fun uninstalledDao() : UninstallDao
}