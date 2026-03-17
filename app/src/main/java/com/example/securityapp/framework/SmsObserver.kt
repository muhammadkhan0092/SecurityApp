package com.example.securityapp.framework

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.util.Log
import com.example.securityapp.core.domain.usecase.HandleMessageIntent
import com.example.securityapp.sms.SmsReceiverEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsObserver(
    handler: Handler,
    private val context: Context,
    private val handleMessageIntent: HandleMessageIntent
) : ContentObserver(handler) {

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Log.d("KHAN","ON CHANGE")
        val sms = SmsReader.getLatestSms(context) ?: return
        val lastId = SmsPrefs.getLastSmsId(context)

        if (sms.id > lastId) {
            Log.d("SMS_MONITOR", "New SMS")
            Log.d("SMS_MONITOR", "Sender: ${sms.sender}")
            Log.d("SMS_MONITOR", "Message: ${sms.body}")
            SmsPrefs.saveLastSmsId(context, sms.id)
            CoroutineScope(Dispatchers.IO).launch {
                handleMessageIntent(sms.sender,sms.body)
            }
        }
    }
}