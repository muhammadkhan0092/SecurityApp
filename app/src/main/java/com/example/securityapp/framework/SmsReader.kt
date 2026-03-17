package com.example.securityapp.framework

import android.content.Context
import android.net.Uri

object SmsReader {

    fun getLatestSms(context: Context): SmsData? {

        val cursor = context.contentResolver.query(
            Uri.parse("content://sms/inbox"),
            arrayOf("_id", "address", "body", "date"),
            null,
            null,
            "date DESC"
        )

        cursor?.use {

            if (it.moveToFirst()) {

                val id = it.getLong(it.getColumnIndexOrThrow("_id"))
                val sender = it.getString(it.getColumnIndexOrThrow("address"))
                val body = it.getString(it.getColumnIndexOrThrow("body"))

                return SmsData(id, sender, body)
            }
        }

        return null
    }
}