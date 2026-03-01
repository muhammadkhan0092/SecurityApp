package com.example.securityapp.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.securityapp.core.data.models.MessagesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ControllerMessagesDao{
    @Insert
    suspend fun insert(data : MessagesEntity)
    @Delete
    suspend fun delete(data : List<MessagesEntity>)
    @Query("SELECT * FROM controller_messages")
    fun getAllFlow() : Flow<List<MessagesEntity>>
    @Query("SELECT * FROM controller_messages where email=:email")
    suspend fun getData(email : String) : List<MessagesEntity>
    @Query("SELECT * FROM controller_messages where email=:email")
    fun getFlow(email: String) : Flow<List<MessagesEntity>>
}