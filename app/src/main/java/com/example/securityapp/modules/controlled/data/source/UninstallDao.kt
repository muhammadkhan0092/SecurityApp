package com.example.securityapp.modules.controlled.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.securityapp.modules.controlled.data.models.UninstallEntity

@Dao
interface UninstallDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(data : List<UninstallEntity>)


    @Query("SELECT * FROM uninstall_entity")
    suspend fun getList() : List<UninstallEntity>

}