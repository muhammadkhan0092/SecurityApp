package com.example.securityapp

import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class ProvisioningModeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = getIntent()
        var provisioningMode = 1
        val allowedProvisioningModes: MutableList<Int?>? =
            intent.getIntegerArrayListExtra(DevicePolicyManager.EXTRA_PROVISIONING_ALLOWED_PROVISIONING_MODES)

        if (allowedProvisioningModes!!.contains(DevicePolicyManager.PROVISIONING_MODE_FULLY_MANAGED_DEVICE)) provisioningMode =
            DevicePolicyManager.PROVISIONING_MODE_FULLY_MANAGED_DEVICE
        else if (allowedProvisioningModes.contains(DevicePolicyManager.PROVISIONING_MODE_MANAGED_PROFILE)) provisioningMode =
            DevicePolicyManager.PROVISIONING_MODE_MANAGED_PROFILE

        val resultIntent = Intent()
        resultIntent.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_MODE, provisioningMode)

        setResult(RESULT_OK, resultIntent)
        finish()
    }
}