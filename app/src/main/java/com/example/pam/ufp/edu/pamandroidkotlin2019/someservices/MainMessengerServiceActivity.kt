package com.example.pam.ufp.edu.pamandroidkotlin2019.someservices

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.pam.ufp.edu.pamandroidkotlin2019.R

import kotlinx.android.synthetic.main.activity_main_messenger_service.*
import kotlinx.android.synthetic.main.content_main_messenger_service.*


/**
 * Only activities, services, and content providers can bind to a service;
 * Cannot bind to a service from a broadcast receiver.
 *
 * For service to respond to client, create a Messenger in the client, so that client receives the
 * onServiceConnected() callback, it sends a Message to the service that includes the client's
 * Messenger in the replyTo parameter of the send() method.
 *
 */
class MainMessengerServiceActivity : AppCompatActivity() {

    /** Messenger for communicating with the service.  */
    private var serviceMessenger: Messenger? = null
    /** Flag indicating whether we have called bind on the service.  */
    private var serviceBound: Boolean = false

    /** Messenger for service communicating back with client.  */
    private var clientMessenger: Messenger? = null

    /** Object for interacting with the main interface of the service. */
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, serviceBinder: IBinder) {
            // This is called when the connection with the service has been established, giving an
            // object we can use to interact with the service.
            // We communicate with the service using a Messenger (cf. client-side representation
            // of the raw IBinder object).
            serviceMessenger = Messenger(serviceBinder)
            serviceBound = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            serviceMessenger = null
            serviceBound = false
        }
    }

    /** Handler for incoming messages from service via Messenger. */
    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_TO_CLIENT_REPLY_WORLD -> {
                    Toast.makeText(applicationContext, "Hello on client side!", Toast.LENGTH_SHORT).show()
                    Log.e(this.javaClass.simpleName, "handleMessage(): client side msg.obj=${msg.obj}")
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    fun sayHelloToService(v: View) {
        if (!serviceBound) return
        // Create and send a message to the service, using a supported 'what' value
        try {
            //Prepare a message to be sent to service
            //val msg: Message = Message.obtain(null, MSG_TO_SERVICE_SEND_HELLO, 0, 0)
            val msg: Message = Message.obtain(null, MSG_TO_SERVICE_SEND_HELLO, "Hello World from client!")
            //Send also the Messenger for callback
            msg.replyTo = clientMessenger
            serviceMessenger?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /** Bind could be done during onCreate() and unbind during onDestroy() whenever the activity
     * needs to receive responses even while it is stopped in the background (but may increase
     * the weight of the process and probability of system to kill it). */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_messenger_service)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (this.clientMessenger==null){
            clientMessenger = Messenger(IncomingHandler(this))
        }

        buttonSayHello.setOnClickListener {
            sayHelloToService(it)
        }
    }

    /** Bind during onStart() assures interaction with service only while activity is visible */
    override fun onStart() {
        super.onStart()
        // Use an Intent to bind to the service, i.e. call bindService():
        // binding is asynchronous, hence bindService() returns immediately without returning the
        // IBinder to the client. The client must create an instance of ServiceConnection and pass
        // it to bindService() to receive the IBinder (ServiceConnection includes a callback method
        // to deliver the IBinder back to client).
        Intent(this, MessengerService::class.java).also {
                intent -> bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    /** Unbind during onStop() assures interaction with service stops while activity is not visible */
    override fun onStop() {
        super.onStop()
        // Unbind from the service
        if (serviceBound) {
            unbindService(serviceConnection)
            serviceBound = false
        }
    }

}

