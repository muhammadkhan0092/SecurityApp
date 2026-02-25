package com.example.securityapp.modules.controlled.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ControlledDao{
    @Insert
    suspend fun insert(data : List<ControlledEntity>)
    @Delete
    suspend fun delete(data : List<ControlledEntity>)
    @Query("SELECT * FROM controller_entity")
    fun getFlow() : Flow<List<ControlledEntity>>
}