package com.example.parkingapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.telephony.SmsManager
import androidx.lifecycle.LifecycleService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ServiceSMS: LifecycleService() {
    private var job: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val stopTime = intent?.getLongExtra("stopTime", 0L) ?: 0L
        val smsNumber = intent?.getStringExtra("smsNumber") ?: return START_NOT_STICKY
        val message = intent.getStringExtra("message") ?: "Hello!"

        startForeground(1, createNotification())

        job = CoroutineScope(Dispatchers.IO).launch {
            while (System.currentTimeMillis() < stopTime) {
                sendSMS(smsNumber, message)
                delay(3600000) // 1 hour
            }
            stopSelf()
        }

        return START_STICKY
    }

    private fun createNotification(): Notification {
        val channelId = "sms_service_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "SMS Service",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        return Notification.Builder(this, channelId)
            .setContentTitle("SMS Service Running")
            .setContentText("Sending SMS every hour")
            .build()
    }

    private fun sendSMS(number: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(number, null, message, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}