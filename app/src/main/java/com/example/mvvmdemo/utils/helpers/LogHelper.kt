package com.example.mvvmdemo.utils.helpers

import android.util.Log

/**
 * This is the helper class which contains all the methods or functions regarding Logs.
 */
object LogHelper {
    private var TAG = javaClass.simpleName

    // public var flag: Boolean? = false

    internal fun verbose(tag: String, msg: String) {
        //  if (BuildConfig.DEBUG)
        Log.v(tag, msg)
    }

    internal fun debug(tag: String, msg: String) {
        //   if (BuildConfig.DEBUG)
        Log.d(tag, msg)
    }

    internal fun info(tag: String, msg: String) {
        //  if (BuildConfig.DEBUG)
        Log.i(tag, msg)
    }

    internal fun warn(tag: String, msg: String) {
        // if (BuildConfig.DEBUG)
        Log.w(tag, msg)
    }

    internal fun error(tag: String, msg: String) {
        //if (BuildConfig.DEBUG)
        Log.e(tag, msg)
    }
}