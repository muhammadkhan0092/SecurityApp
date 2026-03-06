
package com.example.securityapp.permissions

import android.Manifest

object RuntimePermissions {

    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_PHONE_NUMBERS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS
    )
}