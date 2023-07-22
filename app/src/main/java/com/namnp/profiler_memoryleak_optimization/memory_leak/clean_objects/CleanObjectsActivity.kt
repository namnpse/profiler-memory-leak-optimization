package com.namnp.profiler_memoryleak_optimization.memory_leak.clean_objects

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.namnp.profiler_memoryleak_optimization.R


class CleanObjectsActivity : AppCompatActivity() {

//private class ThreadedTask : Thread() {
//    override fun run() {
//        // Run the ThreadedTask for some time
//        SystemClock.sleep((1000 * 20).toLong())
//    }
//}
//-> use static class -> static class does not have a reference to the enclosing activity class

    companion object {
        private class ThreadedTask : Thread() {
            override fun run() {
                // check if the thread is interrupted
                while (!isInterrupted) {
                    // Run the ThreadedTask for some time
                    SystemClock.sleep((1000 * 20).toLong())
                }
            }
        }
    }

    private var thread: ThreadedTask? = null
    private var handler: Handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clean_objects)

        // THREAD
        val thread = ThreadedTask()
        thread.start()

        // HANDLER
        handler.postDelayed(
            {
                println("Handler execution done")
            },
            (1000 * 10).toLong()  // delay its execution.
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        //kill the thread in activity onDestroy
        thread?.interrupt()

        //remove the handler references and callbacks.
        handler.removeCallbacksAndMessages(null)
    }
}

/*
NOTE:
There are many instances where leaks can occur. Other instances where leaks can take place include:
    Listeners (Broadcast)
    Observable (should use LiveData cause it has lifecycle)
    Disposables
    Fragments
    Lazy binding
    ListView binding
    Bitmap objects
    Inner classes – non-static inner classes and anonymous inner classes
    AsyncTask
    Location managers
    Resource objects, such as a cursor or file
*/
