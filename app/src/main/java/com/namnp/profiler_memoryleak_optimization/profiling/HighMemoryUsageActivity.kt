package com.namnp.profiler_memoryleak_optimization.profiling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namnp.profiler_memoryleak_optimization.R

import android.graphics.Color
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

/**
 * In order to stress the memory usage,
 * this activity creates 100000 rows of TextView when user clicks on the start button
 */
class HighMemoryUsageActivity : AppCompatActivity() {

    private val NO_OF_TEXTVIEWS_ADDED = 100000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_memory_usage)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.activity_name_high_memory_usage)
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            addRowsOfTextView()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Add rows of text views to the root LinearLayout
     */
    private fun addRowsOfTextView() {
        val linearLayout = findViewById<LinearLayout>(R.id.linear_layout)

        val textViewParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val textViews = arrayOfNulls<TextView>(NO_OF_TEXTVIEWS_ADDED)

        for (i in 0 until NO_OF_TEXTVIEWS_ADDED) {
            textViews[i] = TextView(this)
            textViews[i]?.layoutParams = textViewParams
            textViews[i]?.text = i.toString()
            textViews[i]?.setBackgroundColor(getRandomColor())
            linearLayout.addView(textViews[i])
            linearLayout.invalidate()
        }
    }

    /**
     * Creates a random color for background color of the text view.
     */
    private fun getRandomColor(): Int {
        val r = Random()
        val red = r.nextInt(255)
        val green = r.nextInt(255)
        val blue = r.nextInt(255)

        return Color.rgb(red, green, blue)
    }
}