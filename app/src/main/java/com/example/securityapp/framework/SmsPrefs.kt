package com.example.securityapp.framework

import android.content.Context

object SmsPrefs {

    private const val PREF_NAME = "sms_monitor"
    private const val KEY_LAST_SMS_ID = "last_sms_id"

    fun getLastSmsId(context: Context): Long {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(KEY_LAST_SMS_ID, -1)
    }

    fun saveLastSmsId(context: Context, id: Long) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putLong(KEY_LAST_SMS_ID, id).apply()
    }
}