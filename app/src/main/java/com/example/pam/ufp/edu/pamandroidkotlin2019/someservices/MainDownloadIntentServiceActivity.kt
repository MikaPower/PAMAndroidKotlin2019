package com.example.pam.ufp.edu.pamandroidkotlin2019.someservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.pam.ufp.edu.pamandroidkotlin2019.R

import kotlinx.android.synthetic.main.activity_main_download_intent_service.*
import kotlinx.android.synthetic.main.content_main_download_intent_service.*
import kotlinx.android.synthetic.main.content_main_hit.*

class MainDownloadIntentServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_download_intent_service)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        buttonDownload.setOnClickListener {
            var url:String = textViewUrl.text.toString()
            var filename:String = textViewFilename.text.toString()
            DownloadIntentService.startActionFoo(applicationContext,url,filename)
            var statusIntentFilter1 = IntentFilter(BROAD_CAST_DOWNLOAD_STATUS_ACTION)
            var downloadStateReceiver = DownloadStateReceiver(editMultilinetFileContent)
            LocalBroadcastManager.getInstance(this).registerReceiver(downloadStateReceiver,statusIntentFilter1);


        }
    }
    /**
     * Broadcast receiver for receiving status updates from the IntentService.
     */
    private class DownloadStateReceiver(editTextOutput: EditText) : BroadcastReceiver() {

        val editTextOutput: EditText = editTextOutput
        override fun onReceive(context: Context, intent: Intent) {
            /*
             * Handle Intents here.
             */
            val status: String? = intent.getStringExtra(EXTENDED_DATA_STATUS)

            Log.e(this.javaClass.simpleName, "onReceive(): status=${status}")
            editTextOutput.setText(status)
        }
    }
}
