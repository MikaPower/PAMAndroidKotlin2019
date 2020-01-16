package com.example.pam.ufp.edu.pamandroidkotlin2019.someservices

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import okhttp3.OkHttpClient
import okhttp3.Request

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_DOWNLOAD_FILE =
    "com.example.pam.ufp.edu.pamandroidkotlin2019.someservices.action.FOO"
private const val ACTION_BAZ =
    "com.example.pam.ufp.edu.pamandroidkotlin2019.someservices.action.BAZ"
const val BROAD_CAST_DOWNLOAD_STATUS_ACTION =   "com.example.pam.ufp.edu.pamandroidkotlin2019.someservices.BROAD_CAST_DOWNLOAD_STATUS_ACTION"

const val EXTENDED_DATA_STATUS  =  "com.example.pam.ufp.edu.pamandroidkotlin2019.someservices.EXTENDED_DATA_STATUS"

// TODO: Rename parameters
private const val PARAM1_URL =
    "com.example.pam.ufp.edu.pamandroidkotlin2019.someservices.extra.PARAM1"
private const val PARAM2_FILENAME =
    "com.example.pam.ufp.edu.pamandroidkotlin2019.someservices.extra.PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class DownloadIntentService : IntentService("DownloadIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_DOWNLOAD_FILE -> {
                val url = intent.getStringExtra(PARAM1_URL)
                val filename = intent.getStringExtra(PARAM2_FILENAME)
                handleActionDownloadFile(url, filename)
            }
            ACTION_BAZ -> {
                val param1 = intent.getStringExtra(PARAM1_URL)
                val param2 = intent.getStringExtra(PARAM2_FILENAME)
                handleActionBaz(param1, param2)
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String, param2: String) {
        TODO("Handle action Foo")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String, param2: String) {
        TODO("Handle action Baz")
    }

    private fun handleActionDownloadFile(url:String,filename:String){
        val resource:String="${url}/${filename}"
        val responseBody= runHttpGetCall(resource)
        returnResponseBackToActivity(responseBody?:"Nok")

    }

    private fun returnResponseBackToActivity(status: String) {
        val localIntent= Intent(BROAD_CAST_DOWNLOAD_STATUS_ACTION).apply { putExtra(
            EXTENDED_DATA_STATUS,status); }
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent)

    }

    private fun runHttpGetCall(url: String) :String?{
        var urlTest = "10.10.40.128:8888/sendFarrusco.html?"
        var client = OkHttpClient()
        var request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).execute()

        val responseBody = response.body?.string()
        Log.e(this.javaClass.simpleName, "call(): response" + response.request)
        Log.e(this.javaClass.simpleName, "call(): messages" + response.message)
        Log.e(this.javaClass.simpleName, "all():body=$responseBody")

        return responseBody
    }

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, DownloadIntentService::class.java).apply {
                action = ACTION_DOWNLOAD_FILE
                putExtra(PARAM1_URL, param1)
                putExtra(PARAM2_FILENAME, param2)
            }
            context.startService(intent)
        }

        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, DownloadIntentService::class.java).apply {
                action = ACTION_BAZ
                putExtra(PARAM1_URL, param1)
                putExtra(PARAM2_FILENAME, param2)
            }
            context.startService(intent)
        }
    }
}
