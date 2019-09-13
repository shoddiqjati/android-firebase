package com.jati.dev.androidfirebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.jati.dev.androidfirebase.utils.VersionChecker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), VersionChecker.OnUpdateNeededListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAppVersion()

        VersionChecker.with(this).onUpdateNeeded(this).check()
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            Log.d("fcmToken", it.result?.token)
        }
    }

    private fun checkAppVersion() {
        tvVersion.text = packageManager.getPackageInfo(packageName, 0).versionName
    }

    private fun snackbar(message: String) {
        Snackbar.make(constraint, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onUpdateNeeded(updateUrl: String) {
        snackbar(updateUrl)
    }
}
