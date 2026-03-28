package com.example.securityapp.modules.controlled.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.securityapp.modules.controlled.data.models.ControlledEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ControlledDao{
    @Insert
    suspend fun insert(data : List<ControlledEntity>)
    @Delete
    suspend fun delete(data : List<ControlledEntity>)
    @Query("SELECT * FROM controlled_entity")
    fun getFlow() : Flow<List<ControlledEntity>>

    @Query("SELECT * FROM controlled_entity")
    suspend fun getList() : List<ControlledEntity>

    @Query("DELETE FROM controlled_entity")
    suspend fun deleteAllControlled()
}