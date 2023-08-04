package com.namnp.profiler_memoryleak_optimization.memory_leak.leak_canary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.namnp.profiler_memoryleak_optimization.R

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, SecondActivity::class.java).also {
            startActivity(it)
        }

        Intent().apply {
            action = "Broadcast"
            putExtra("name", "Namnpse")
        }.also {
            LocalBroadcastManager.getInstance(this).sendBroadcast(it)
        }
    }

    companion object {
        lateinit var context: Context
    }
}