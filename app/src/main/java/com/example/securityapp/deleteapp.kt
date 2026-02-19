package com.example.securityapp

import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

fun deleteApp(context: Context){
    val dpm = context.getSystemService(DevicePolicyManager::class.java)
    val comp = ComponentName(context, MyDeviceAdminReceiver::class.java)
    dpm.clearPackagePersistentPreferredActivities(comp, "com.target.app")
    val intent = Intent(Intent.ACTION_DELETE)
    intent.data = Uri.parse("package:com.target.app")
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}
const val CODE_UNINSTALL_RESULT = 1235
const val ACTION_UNINSTALL_RESULT = "eu.sisik.removehideaps.ACTION_UNINSTALL_RESULT"
internal fun uninstallPackage(context: Context, packageName: String) {
    Log.d("KHAN","IN UNINSTALL")
    val intentSender = PendingIntent.getBroadcast(context,
        CODE_UNINSTALL_RESULT,
        Intent(ACTION_UNINSTALL_RESULT),
        PendingIntent.FLAG_IMMUTABLE).intentSender

    val pi = context.packageManager.packageInstaller
    pi.uninstall(packageName, intentSender)
}
