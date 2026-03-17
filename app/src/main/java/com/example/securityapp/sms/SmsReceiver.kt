package com.example.securityapp.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("KHAN","INTENT IS ${intent.action}")
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle: Bundle? = intent.extras
            try {
                if (bundle != null) {
                    val pdus = bundle["pdus"] as Array<*>
                    val format = bundle.getString("format")

                    for (pdu in pdus) {

                        val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray, format)

                        val sender = smsMessage.displayOriginatingAddress
                        val messageBody = smsMessage.displayMessageBody

                        Log.d("SMS_RECEIVED", "From: $sender")
                        Log.d("SMS_RECEIVED", "Message: $messageBody")
                        CoroutineScope(Dispatchers.IO).launch {
                            SmsReceiverEntryPoint.get(context).invoke(sender,messageBody)
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("SMS_RECEIVED", "Error: ${e.message}")
            }
        }
    }
}
