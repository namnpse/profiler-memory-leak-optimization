package com.namnp.profiler_memoryleak_optimization.memory_leak.singleton

import android.content.Context
import java.lang.ref.WeakReference

class SingletonWithLeakage private constructor(private val context: Context) {

    companion object {

        private var instance: SingletonWithLeakage? = null

        fun getInstance(context: Context): SingletonWithLeakage? {
            if (instance == null) {
                instance = SingletonWithLeakage(context)
            }
            return instance
        }
    }
}

//object SingletonWithLeakage {
//
//}

//Start screen A -> screen B -> init singleton in B (strong reference),
//when finished B -> singleton hold context reference of B -> GC cannot collect B -> leak
//-> Solution:
//    1. use applicationContext
//    2. use WeakReference

class SingletonNoLeakage private constructor(private val context: WeakReference<Context>) {

    companion object {
        private var ourInstance: SingletonNoLeakage? = null

        fun getInstance(context: Context): SingletonNoLeakage? {
            if (ourInstance == null) {
                ourInstance = SingletonNoLeakage(WeakReference(context))
            }
            return ourInstance
        }
    }
}