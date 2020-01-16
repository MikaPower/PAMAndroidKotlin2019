package com.example.pam.ufp.edu.pamandroidkotlin2019.someservices

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.delay
import java.lang.Thread.sleep

public const val MSG_TO_SERVICE_SEND_HELLO = 1
public const val MSG_TO_CLIENT_REPLY_WORLD = 2
public const val MSG_TO_REGISTER_CLIENT_MESSENGER = 3

/**
 * Service to perform multi-threading tasks
 * (instead of processing start requests through a work queue)
 *
 * A service provides an IBinder (programming interface that clients use to call the service).
 * There are 3 ways to define the interface:
 *
 *  Extending the Binder class:
 *      when service is merely a background worker for your own application;
 *      service private to own application and runs in same process as client (which is common);
 *      create interface by extending the Binder class and return instance from onBind();
 *
 *  Using a Messenger:
 *      when service works across different processes, create an interface with a Messenger;
 *      service defines an Handler that responds to different types of Message objects;
 *      the Handler is the basis for a Messenger that can then share the IBinder;
 *      client can define a Messenger of its own, so the service can send messages back;
 *      Messenger queues all requests into single thread (service needs not be thread-safe).
 *
 *  Using AIDL:
 *      Android Interface Definition Language (AIDL) decomposes objects into primitives that the
 *      operating system can understand and marshals them across processes to perform IPC;
 *      The previous technique (Messenger) is actually based on AIDL as its underlying structure;
 *      Must create an .aidl file that defines the programming interface (similar to RMI).
 *
 */
class MessengerService : Service() {

    /** Target published for clients to send messages to IncomingHandler. */
    private lateinit var serviceMessenger: Messenger

    /** Target published for service to send messages to client. */
    private lateinit var clientMessenger: Messenger

    /** Handler for incoming messages from clients via Messenger. */
    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_TO_SERVICE_SEND_HELLO -> {
                    Toast.makeText(applicationContext, "Hello on service side!", Toast.LENGTH_SHORT).show()
                    Log.e(
                        this.javaClass.simpleName,
                        "handleMessage(): service side msg.obj=${msg.obj}"
                    )

                    val msgToClient: Message = Message.obtain(null, MSG_TO_CLIENT_REPLY_WORLD, "Ok from Service!")
                    msg.replyTo.send(msgToClient)
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    /**
     * When client binds to the service, it returns an interface handler to the service Messenger,
     * so that client can send messages to the service.
     */
    override fun onBind(intent: Intent): IBinder {
        //TODO("Return the communication channel that client uses to send msg to the service.")
        Toast.makeText(applicationContext, "binding", Toast.LENGTH_SHORT).show()
        serviceMessenger = Messenger(IncomingHandler(this))
        return serviceMessenger.binder
    }
}

