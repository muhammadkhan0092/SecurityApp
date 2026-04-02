package com.example.securityapp.modules.messages.data.framework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("KHAN", "INTENT ACTION: ${intent.action}")
        if (intent.action == Telephony.Sms.Intents.SMS_DELIVER_ACTION) {
            val pendingResult = goAsync()

            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    for (sms in messages) {
                        val sender = sms.displayOriginatingAddress ?: "Unknown"
                        val messageBody = sms.displayMessageBody ?: ""

                        Log.d("SMS_RECEIVED", "From: $sender | Message: $messageBody")

                        // Execute your logic
                        SmsReceiverEntryPoint.Companion.get(context).invoke(sender, messageBody)
                    }
                } catch (e: Exception) {
                    Log.e("SMS_RECEIVED", "Error processing SMS: ${e.message}")
                } finally {
                    // MUST call finish() so the system can recycle the receiver
                    pendingResult.finish()
                }
            }
        }
    }
}