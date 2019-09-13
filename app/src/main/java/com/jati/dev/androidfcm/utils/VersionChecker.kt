package com.jati.dev.androidfcm.utils

import android.content.Context
import android.text.TextUtils
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


/**
 * Created by Jati on 19/11/18.
 */

class VersionChecker(private val context: Context,
                     private val onUpdateNeededListener: OnUpdateNeededListener?) {

    interface OnUpdateNeededListener {
        fun onUpdateNeeded(updateUrl: String)
    }

    fun check() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()

        if (remoteConfig.getBoolean(KEY_REQUIRED_STATUS)) {
            val currentVersion = remoteConfig.getString(KEY_LATEST_VERSION)
            val appVersion = getAppVersion(context)
            val updateUrl = remoteConfig.getString(KEY_UPDATE_URL)

            if (!TextUtils.equals(currentVersion, appVersion) && onUpdateNeededListener != null) {
                onUpdateNeededListener.onUpdateNeeded(updateUrl)
            }
        }
    }

    private fun getAppVersion(context: Context): String = context.packageManager
            .getPackageInfo(context.packageName, 0)
            .versionName.apply { replace("[a-zA-Z]|-".toRegex(), "") }

    class Builder(private val context: Context) {
        private var onUpdateNeededListener: OnUpdateNeededListener? = null

        fun onUpdateNeeded(onUpdateNeededListener: OnUpdateNeededListener): Builder {
            this.onUpdateNeededListener = onUpdateNeededListener
            return this
        }

        fun build(): VersionChecker {
            return VersionChecker(context, onUpdateNeededListener)
        }

        fun check(): VersionChecker {
            val forceUpdateChecker = build()
            forceUpdateChecker.check()

            return forceUpdateChecker
        }
    }

    companion object {
        const val KEY_UPDATE_URL = "update_url"
        const val KEY_LATEST_VERSION = "latest_version"
        const val KEY_REQUIRED_STATUS = "required_status"

        fun with(context: Context): Builder {
            return Builder(context)
        }
    }
}