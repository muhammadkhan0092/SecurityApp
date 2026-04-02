package com.example.securityapp.modules.device_owner.data

import android.content.Context
import com.example.securityapp.modules.device_owner.domain.DeviceOwnerRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DeviceOwnerEntryPoint {
    val repository: DeviceOwnerRepository
    companion object {
        fun get(context: Context): DeviceOwnerRepository {
            return EntryPointAccessors.fromApplication(context.applicationContext, DeviceOwnerEntryPoint::class.java).repository
        }
    }
}
