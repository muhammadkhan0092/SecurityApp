package com.example.securityapp.modules.phone.data

import android.Manifest
import android.content.Context
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.securityapp.modules.phone.domain.PhoneRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TelephoneRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : PhoneRepository {
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    override fun getSimNumbers(): List<String> {
        val subscriptionManager =
            context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
        val simNumbers = mutableListOf<String>()
        val activeSubs = subscriptionManager.activeSubscriptionInfoList
        Log.d("KHAN","ACTIVE SUBS ARE $activeSubs")
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

    override fun isAirplaneModeOn(): Boolean {
        return try {
            Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON) != 0
        } catch (e: Settings.SettingNotFoundException) {
            false
        }
    }
}