package com.example.pam.ufp.edu.pamandroidkotlin2019.farrusco

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.pam.ufp.edu.pamandroidkotlin2019.R
import okhttp3.OkHttpClient

import kotlinx.android.synthetic.main.activity_main_farrusco.*
import kotlinx.android.synthetic.main.content_main_farrusco.*
import okhttp3.Request
import java.net.URL

class MainFarruscoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_farrusco)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        imageButtonUp.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                setFarruscoMovementAndLauch("12")
            }
        })

        imageButtonRight.setOnClickListener {
            setFarruscoMovementAndLauch("3")

        }

        imageButtonDown.setOnClickListener {
            setFarruscoMovementAndLauch("9")

        }

        imageButtonLeft.setOnClickListener {
            setFarruscoMovementAndLauch("6")
        }
    }

    private fun setFarruscoMovementAndLauch(move: String) {
        var url = editTextUrl.text.toString()
        var time = editTextId.text.toString()
        var id = editTextId.text.toString()
        var queryStr = "?i=$id&m=$move&t=$time"
        Log.e(this.javaClass.simpleName, "setFarruscoMovementandLauch(): queryStr=" + queryStr)



        GetHttpTask(this.textViewBigBox).execute(url, queryStr)
    }


    private class GetHttpTask(textViewOutput: TextView) : AsyncTask<String, Unit, String>() {
        @SuppressLint("StaticFieldLeak")
        val innerTextView: TextView? = textViewOutput
        var reply: String? = ""
        override fun doInBackground(vararg params: String?): String? {
            val url = URL("http://10.100.126.117:8888/sendFarrusco.html")
            Log.e(this.javaClass.simpleName, "doInBackground(): url=$url")
            reply = runHttpGetCall(url.toString())
            return reply
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.e(this.javaClass.simpleName, "result:" + result)
            innerTextView?.text = result
        }

        fun runHttpGetCall(url: String): String? {
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
    }
}
