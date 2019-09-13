package com.jati.dev.androidfcm

import android.app.Application
import android.content.Intent
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jati.dev.androidfcm.utils.VersionChecker

/**
 * Created by Jati on 19/11/18.
 */

class FirebaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val remoteHash = hashMapOf<String, String>()
        val remoteConfig = FirebaseRemoteConfig.getInstance()

        remoteHash[VersionChecker.KEY_REQUIRED_STATUS] = "false"
        remoteHash[VersionChecker.KEY_UPDATE_URL] = "sample.org"
        remoteHash[VersionChecker.KEY_LATEST_VERSION] = "1.0"

        remoteConfig.setDefaults(remoteHash.toMap())
        remoteConfig.fetch(10).addOnCompleteListener {
            if (it.isSuccessful) remoteConfig.activateFetched()
        }
    }
}