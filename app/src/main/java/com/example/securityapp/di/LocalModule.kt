package com.example.securityapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.example.securityapp.core.data.db.AppSettingsSerializer
import com.example.securityapp.core.data.db.SecurityDb
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.controlled.data.source.ControlledDao
import com.example.securityapp.modules.controller.data.dao.ControllerDao
import com.example.securityapp.core.data.dao.ControllerMessagesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {
    @Singleton
    @Provides
    fun returnDataStore(@ApplicationContext context: Context): DataStore<AppSettings> {
        return DataStoreFactory.create(
            produceFile = { context.dataStoreFile("app_settings.pb") },
            serializer = AppSettingsSerializer,
        )
    }

    @Singleton
    @Provides
    fun returnRoomDb(
        @ApplicationContext context: Context
    ): SecurityDb {
        return Room.databaseBuilder(
            context = context,
            SecurityDb::class.java,
            "security_db"
        ).build()
    }


    @Provides
    @Singleton
    fun returnControllerDao(db : SecurityDb) : ControllerDao{
        return db.controllerDao()
    }
    @Provides
    @Singleton
    fun returnControlledDao(db : SecurityDb) : ControlledDao{
        return db.controlledDao()
    }

    @Provides
    @Singleton
    fun returnControllerMessagesDao(db : SecurityDb) : ControllerMessagesDao {
        return db.controllerMessagesDao()
    }
}