package com.namnp.profiler_memoryleak_optimization.memory_leak.leak_canary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private var broadcastReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirstActivity.context = this
    }

    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }

    private fun registerBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                //your receiver code goes here!
            }
        }
        registerReceiver(broadcastReceiver, IntentFilter("SmsMessage.intent.MAIN"))
    }

    override fun onStop() {
        super.onStop()

        /*
         * Uncomment the line below in order to avoid memory leak.
         * Need to unregister the broadcast receiver since the broadcast receiver keeps a reference of the activity.
         * Now when its time for your Activity to be killed, the Android framework will call onDestroy() on it
         * but the garbage collector will not be able to remove the instance from memory because the broadcastReceiver
         * is still holding a strong reference to it.
         * */
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
        }
    }

}