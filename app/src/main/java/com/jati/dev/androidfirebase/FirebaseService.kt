package com.jati.dev.androidfirebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        Log.d("fcmMessage", p0?.toString())
    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.d("fcmToken", p0.toString())
    }
}