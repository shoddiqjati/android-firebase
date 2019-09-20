package com.jati.dev.androidfcm

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.jati.dev.androidfcm.utils.VersionChecker
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), VersionChecker.OnUpdateNeededListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VersionChecker.with(this).onUpdateNeeded(this).check()
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            task.result?.token.let { token.text = it }
        }

        send.setOnClickListener {
            if (phone.text.isNotEmpty()) sendToWhatsApp()
        }

        token.setOnLongClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("token", token.text.toString())
            cm.primaryClip = clipData
            Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun snackbar(message: String) {
        Snackbar.make(constraint, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onUpdateNeeded(updateUrl: String) {
        snackbar(updateUrl)
    }

    private fun sendToWhatsApp() {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data =
                Uri.parse("http://api.whatsapp.com/send?phone=${phone.text}&text=${token.text.toString()}")
        })
    }
}
