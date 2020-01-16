package com.example.pam.ufp.edu.pamandroidkotlin2019.someservices

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.pam.ufp.edu.pamandroidkotlin2019.R

import kotlinx.android.synthetic.main.activity_main_random_bind_local_service.*
import kotlinx.android.synthetic.main.content_main_random_bind_local_service.*

class MainRandomBindLocalServiceActivity : AppCompatActivity() {

    private lateinit var mService: RandomBindLocalService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.e(this.javaClass.simpleName, "onServiceConnected(): going get RandomBindLocalService...")

            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as RandomBindLocalService.RandomLocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_random_bind_local_service)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(this.javaClass.simpleName, "onStart(): going to bindService()...")

        // Bind to LocalService
        Intent(this, RandomBindLocalService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    /** Clients should unbind from services at appropriate times */
    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    /**
     * Called when buttonRandomService clicked (in layout associate button with this
     * method through the attribute android:onClick="onButtonRandomServiceClick")
     */
    fun onButtonRandomServiceClick(v: View) {
        Log.e(this.javaClass.simpleName, "onButtonRandomServiceClick(): going to call RandomBindLocalService...")
        if (mBound) {
            Log.e(this.javaClass.simpleName, "onButtonRandomServiceClick(): mBound=${mBound}")
            // Call a method from the LocalService.
            // If this call does something that might hang, then the request should
            // occur in a separate thread to avoid slowing down the activity performance.
            val num: Int = mService.randomNumber
            Toast.makeText(this, "onButtonRandomServiceClick(): number: $num", Toast.LENGTH_SHORT).show()
            textViewRandomNumber.text = "${num}"
        }
    }
}

