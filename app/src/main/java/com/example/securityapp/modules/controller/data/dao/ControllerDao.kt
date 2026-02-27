package com.example.securityapp.modules.controller.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.securityapp.modules.controller.data.models.ControllerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ControllerDao{
    @Insert
    suspend fun insert(data : List<ControllerEntity>)
    @Delete
    suspend fun delete(data : List<ControllerEntity>)
    @Query("SELECT * FROM controller_entity")
    fun getFlow() : Flow<List<ControllerEntity>>
    @Query("SELECT * FROM controller_entity")
    suspend fun getData() : List<ControllerEntity>
}