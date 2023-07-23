package com.namnp.profiler_memoryleak_optimization.profiling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namnp.profiler_memoryleak_optimization.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NumerousGarbageCollectorActivity: AppCompatActivity() {

    private val NO_OF_TEXTVIEWS_ADDED = 100000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numerous_garbage_collector)
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        val numbers = arrayOfNulls<Int>(NO_OF_TEXTVIEWS_ADDED).mapIndexed { index, _ -> index }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NumerousGCRecyclerViewAdapter(numbers)
        //recyclerView.adapter = LessNumerousGCRecyclerViewAdapter(this,numbers)
    }
}

class NumerousGCRecyclerViewAdapter(private val numbers: List<Int>): RecyclerView.Adapter<NumerousGCViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumerousGCViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_numerous_gc, parent, false)
        return NumerousGCViewHolder(view)
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    override fun onBindViewHolder(vh: NumerousGCViewHolder, position: Int) {
        vh.textView.text = position.toString()

        //Create bitmap from resource
        val bitmap = if(position % 2 == 0)
            BitmapFactory.decodeResource(vh.imageView.context.resources, R.drawable.img_big_bitmap)
        else
            BitmapFactory.decodeResource(vh.imageView.context.resources, R.drawable.img_small_bitmap)
        vh.imageView.setImageBitmap(bitmap)
    }
}

class LessNumerousGCRecyclerViewAdapter(
        private val context: Context,
        private val numbers: List<Int>
    ): RecyclerView.Adapter<NumerousGCViewHolder>() {

    private val bitBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_big_bitmap)
    private val smallBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_small_bitmap)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumerousGCViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_numerous_gc, parent, false)
        return NumerousGCViewHolder(view)
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    override fun onBindViewHolder(vh: NumerousGCViewHolder, position: Int) {
        vh.textView.text = position.toString()

        //Reuse bitmap
        val bitmap = if(position % 2 == 0) bitBitmap else smallBitmap
        vh.imageView.setImageBitmap(bitmap)
    }
}


class NumerousGCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textView: TextView = itemView.findViewById(R.id.text_view)
    var imageView: ImageView = itemView.findViewById(R.id.image_view)
}
