package com.example.securityapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

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
                        if (messageBody.contains("LOCATION")) {
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("SMS_RECEIVED", "Error: ${e.message}")
            }
        }
    }
}
