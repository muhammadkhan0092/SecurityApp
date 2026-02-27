package com.example.securityapp.modules.controller.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.securityapp.modules.controller.data.models.ControllerMessagesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ControllerMessagesDao{
    @Insert
    suspend fun insert(data : ControllerMessagesEntity)
    @Delete
    suspend fun delete(data : List<ControllerMessagesEntity>)
    @Query("SELECT * FROM controller_messages")
    fun getFlow() : Flow<List<ControllerMessagesEntity>>
    @Query("SELECT * FROM controller_entity where email=:email")
    suspend fun getData(email : String) : List<ControllerMessagesEntity>
}