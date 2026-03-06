// SmsReceiverEntryPoint.kt
package com.example.securityapp.sms

import com.example.securityapp.core.domain.usecase.HandleMessageIntent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
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