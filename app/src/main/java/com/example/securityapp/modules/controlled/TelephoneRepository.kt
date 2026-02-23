package com.example.securityapp.modules.controlled

import android.Manifest
import android.content.Context
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TelephoneRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : PhoneRepository {
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    override fun getSimNumbers(): List<String> {
        val subscriptionManager = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
        val simNumbers = mutableListOf<String>()
        val activeSubs = subscriptionManager.activeSubscriptionInfoList
        if (activeSubs != null) {
            for (sub in activeSubs) {
                val phoneNumber = sub.number
                if (!phoneNumber.isNullOrBlank()) {
                    simNumbers.add(phoneNumber)
                }
            }
        }
        return simNumbers
    }
}