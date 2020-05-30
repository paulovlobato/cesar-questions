package com.victordevelopment.questao6

import android.app.Service
import android.content.Intent
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.annotation.SuppressLint
import android.os.*
import com.google.gson.Gson

class MailService: Service() {

    private var messengerService = Messenger(IncomingHandler())
    private lateinit var executorService: ExecutorService

    data class Node(val value: Email) {
        /**
         * Base Node Class
         */
        var nextVal: Node? = null
    }

    data class Email(val from: String, val to: String, val message: String) {
        override fun toString(): String {
            return "Mail from: ${this.from}, to: ${this.to} --- ${this.message}"
        }
    }

    companion object {
        /**
         * Companion for the Logger
         */
        const val LABEL = "MailService"
    }

    override fun onCreate() {
        super.onCreate()

        executorService = Executors.newSingleThreadExecutor()
        Log.i(LABEL, "CREATED APPLICATION")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i(LABEL, "DESTROYED")
    }

    override fun onBind(intent: Intent?): IBinder? {
        if (intent != null) {
            Log.i(LABEL, "BINDING:: ${intent.extras?.getString("clientName")}")
        }
        return messengerService.binder
    }

    private fun sendBroadcast(response: String) {
        val intent = Intent("emailProcessorFinished")
        val extras = Bundle()
        extras.putString("response", response)
        intent.putExtras(extras)
        sendBroadcast(intent)
    }

//    Prevents leak to the outter class
    @SuppressLint("HandlerLeak")
    internal inner class IncomingHandler : Handler() {
        override fun handleMessage(m: Message) {
            super.handleMessage(m)

            val data = m.data
            val dataString = data.getString("email")
            val node = Gson().fromJson(dataString, Node::class.java)

            executorService.submit(Task(node))
        }
    }

    internal inner class Task(private val node: Node) : Runnable {
        override fun run() {
            removeDuplicates(node)
//            System.out.println(node.toString())
            val responseJson = Gson().toJson(node)
            sendBroadcast(responseJson)
        }
    }

    fun removeDuplicates(head: Node?) {
        val hashSet = hashSetOf<Email>()
        var current = head
        var prev: Node? = null

        while (current != null) {
            val value = current.value

            if (hashSet.contains(value)) {
                prev!!.nextVal = current.nextVal
            } else {
                hashSet.add(value)
                prev = current
            }
            current = current.nextVal
        }
    }

}