package com.victordevelopment.questao06sender

import android.content.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var messengerService: Messenger? = null
    var isBound: Boolean = false
    var mailCount = 0

    companion object {
        const val TAG = "MainActivity"
    }

    // DATA OBJECTS
    data class Node(val value: Email) {
        var nextVal: Node? = null
    }

    data class Email(val from: String, val to: String, val message: String) {
        override fun toString(): String {
            return "Mail from: ${this.from}, to: ${this.to} --- ${this.message}"
        }
    }
    // DATA OBJECTS

    // CLASS METHODS
    fun list(head: Node?): String {

        var headAux = head
        val builder = StringBuilder()
        while (headAux != null) {
            builder.append("${headAux.value}, \n \n")
            headAux = headAux.nextVal
        }

        return builder.toString()
    }

    private fun connectionHandler() {
        val intent = Intent()
        intent.`package` = "com.victordevelopment.questao6"
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        intent.putExtra("clientName", "Questao6")
        intent.component = ComponentName("com.victordevelopment.questao6", "com.victordevelopment.questao6.MailService")
        bindService(intent, connectionHandler, Context.BIND_AUTO_CREATE)
    }

    private fun proccess() {
        if (isBound && messengerService != null) {
            val msg = Message.obtain()
            val bundle = Bundle()

//            Emails constructor
            val email1 = Email("mmcp@cesar.org.br", "pvlobato@gmail.com", "Paulo, bem-vindo à CESAR")
            val email2 = Email("pvlobato@gmail.com","mmcp@gmail.com","Tenho dúvidas em relação às questões!")

//            Thread builder
            val head = Node(email1)
            head.nextVal = Node(email2)
            head.nextVal!!.nextVal = Node(email1)

//            Original e-mail resposne
            email.text = "Thread 1 (original message, with duplicates): \n ${list(head)}"

            val emailJson = Gson().toJson(head)
            bundle.putString("email", emailJson)
            msg.data = bundle

            try {
                this.messengerService!!.send(msg)
                mailCount++
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        }

    }
    // CLASS METHODS

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        /**
         * Broadcast Receiver
         */
        override fun onReceive(context: Context?, intent: Intent?) {
            val responseJson = intent?.getStringExtra("response")
            val node = Gson().fromJson(responseJson, Node::class.java)
            result.text = list(node)
        }
    }

    // LIFECYCLE EVENTS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.isEnabled = false
        button.setOnClickListener({ proccess() })

        connectionHandler()

    }

    override fun onResume() {
        val filter = IntentFilter()
        filter.addAction("emailProcessorFinished")
        registerReceiver(receiver, filter)
        super.onResume()
    }

    override fun onStop() {
        unregisterReceiver(receiver)
        super.onStop()
    }

    override fun onDestroy() {
        unbindService(connectionHandler)
        super.onDestroy()
    }
    // LIFECYCLE EVENTS

    // EXTRA HANDLERS
    private val connectionHandler = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            messengerService = Messenger(service)
            isBound = true
            button.isEnabled = true

            Log.i(TAG, "Successfully connected")

            serverStatus.text = getString(R.string.server_status_online)
            serverStatus.setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_green_light))

        }

        override fun onServiceDisconnected(className: ComponentName) {
            messengerService = null
            isBound = false
            button.isEnabled = false

            Log.i(TAG, "Disconnected")

            serverStatus.text = getString(R.string.server_status_offline)
            serverStatus.setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.holo_red_light))
        }
    }
    // EXTRA HANDLERS

}