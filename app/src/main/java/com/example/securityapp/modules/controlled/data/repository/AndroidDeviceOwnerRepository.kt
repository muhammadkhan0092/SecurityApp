package com.example.securityapp.modules.controlled.data.repository

import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.framework.MyDeviceAdminReceiver
import com.example.securityapp.modules.controlled.domain.repository.DeviceOwnerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidDeviceOwnerRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : DeviceOwnerRepository{
    override fun resetPhone(): Result<Unit> {
        return try {
            val dpm = context.getSystemService(DevicePolicyManager::class.java)
            val isOwner = dpm.isDeviceOwnerApp("com.example.securityapp")
            when(isOwner){
                true -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        dpm.wipeDevice(DevicePolicyManager.WIPE_RESET_PROTECTION_DATA)
                    }
                    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        dpm.wipeData(DevicePolicyManager.WIPE_RESET_PROTECTION_DATA)
                    } else {
                        dpm.wipeData(0)
                    }
                    Result.Success(Unit)
                }
                false -> {
                    Log.d("KHAN","DEVICE OWNER NHI HE")
                    Result.Error("Error Resetting Phone, App is not Device Owner")
                }
            }
        }
        catch (e : Exception){
            Log.d("KHAN","ERROR RESETTING PHONE IS ${e.message}")
            Log.d("KHAN","ERROR RESETTING PHONE IS ${e.localizedMessage}")
            Result.Error("Error Resetting Phone")
        }
    }

    override fun deleteApp(){
        val dpm = context.getSystemService(DevicePolicyManager::class.java)
        val comp = ComponentName(context, MyDeviceAdminReceiver::class.java)
        dpm.clearPackagePersistentPreferredActivities(comp, "com.target.app")
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:com.target.app")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
    override fun uninstallPackage(packageName: String): Result<Unit> {
        return try {
            val CODE_UNINSTALL_RESULT = 1235
            val ACTION_UNINSTALL_RESULT = "eu.sisik.removehideaps.ACTION_UNINSTALL_RESULT"
            Log.d("KHAN","IN UNINSTALL")
            val intentSender = PendingIntent.getBroadcast(context,
                CODE_UNINSTALL_RESULT,
                Intent(ACTION_UNINSTALL_RESULT),
                PendingIntent.FLAG_IMMUTABLE).intentSender
            val pi = context.packageManager.packageInstaller
            pi.uninstall(packageName, intentSender)
            Result.Success(Unit)
        }
        catch (e : Exception){
            Result.Error("Error Deleting App")
        }
    }

    override fun isDeviceOwner(): Boolean {
        val dpm = context.getSystemService(DevicePolicyManager::class.java)
        return dpm.isDeviceOwnerApp("com.example.securityapp")
    }
}