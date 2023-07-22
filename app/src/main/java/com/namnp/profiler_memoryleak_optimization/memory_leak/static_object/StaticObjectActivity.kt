package com.namnp.profiler_memoryleak_optimization.memory_leak.static_object

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.namnp.profiler_memoryleak_optimization.R
import java.lang.ref.WeakReference





class StaticObjectActivity : AppCompatActivity() {

    companion object {
        // waring: Do not place Android context classes in static fields -> memory leak
        private var textView: TextView? = null
//        private var mContext: Context? = null // BAD, have to set = null onDestroy()
        private var mContext: WeakReference<Context>? = null // GOOD
        /*static field holds reference to the Activity and live outside of activity lifecycle
       -> activity cannot be collected -> leak
       -> have to destroy reference in onDestroy()*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_static_object)

//        mContext = this // don't use WeakReference, have to set to null if not needed
        mContext = WeakReference<Context>(this) // use WeakReference, don't have to destroy

        changeText()
    }

    private fun changeText() {
        textView = findViewById<View>(R.id.textView) as TextView
        textView?.text = "Update Hello World greetings!"
        textView?.apply {
            text = "Update Hello World greetings!"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        destroy reference in onDestroy()
        textView = null
        mContext = null // not needed if use WeakReference
    }
}

//SOLUTION:
//        1. Avoid use static fields
//        2. Use WeakReference<>
//        3. delete when needed