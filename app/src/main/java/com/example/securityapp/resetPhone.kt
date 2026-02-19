package com.example.securityapp

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.util.Log

fun resetPhone(context : Context){
    val dpm = context.getSystemService(DevicePolicyManager::class.java)
    val isOwner = dpm.isDeviceOwnerApp("com.example.securityapp")

    Log.d("CHECK", "Is Device Owner: $isOwner")
   dpm.setLockTaskPackages(
        ComponentName(context, MyDeviceAdminReceiver::class.java),
        arrayOf("com.example.securityapp")
    )
    dpm.wipeData(0)
    // Inside Activity

}