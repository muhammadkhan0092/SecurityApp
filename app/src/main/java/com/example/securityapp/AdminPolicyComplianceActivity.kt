package com.example.securityapp

import android.os.Bundle
import androidx.activity.ComponentActivity

class AdminPolicyComplianceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_OK)
        finish()
    }
}