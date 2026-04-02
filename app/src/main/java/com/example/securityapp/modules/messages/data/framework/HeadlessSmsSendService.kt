package com.example.securityapp.modules.messages.data.framework

import android.app.Service
import android.content.Intent
import android.os.IBinder

class HeadlessSmsSendService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}