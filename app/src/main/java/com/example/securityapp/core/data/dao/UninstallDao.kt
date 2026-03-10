package com.example.securityapp.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.securityapp.core.data.models.UninstallEntity

@Dao
interface UninstallDao {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun upsert(data : List<UninstallEntity>)


    @Query("SELECT * FROM uninstall_entity")
    suspend fun getList() : List<UninstallEntity>

}