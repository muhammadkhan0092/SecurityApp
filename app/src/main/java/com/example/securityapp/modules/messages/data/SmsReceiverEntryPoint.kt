// SmsReceiverEntryPoint.kt
package com.example.securityapp.modules.messages.data

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import com.example.securityapp.modules.messages.domain.HandleMessageIntent
import dagger.hilt.android.EntryPointAccessors

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SmsReceiverEntryPoint {
    val handleMessageIntent: HandleMessageIntent
    companion object {
        fun get(context: Context): HandleMessageIntent {
            return EntryPointAccessors.fromApplication(context.applicationContext, SmsReceiverEntryPoint::class.java).handleMessageIntent
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SmsService {
    val handleMessageIntent: HandleMessageIntent
    companion object {
        fun get(context: Context): HandleMessageIntent {
            return EntryPointAccessors.fromApplication(context.applicationContext, SmsService::class.java).handleMessageIntent
        }
    }
}