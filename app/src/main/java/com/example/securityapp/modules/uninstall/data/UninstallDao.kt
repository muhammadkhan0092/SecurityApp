package com.example.securityapp.modules.uninstall.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UninstallDao {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun upsert(data : List<UninstallEntity>)


    @Query("SELECT * FROM uninstall_entity")
    suspend fun getList() : List<UninstallEntity>

    @Query("DELETE FROM uninstall_entity")
    suspend fun deleteAll()

}