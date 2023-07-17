package com.example.mvvmdemo.application

import android.os.StrictMode
import androidx.multidex.MultiDexApplication

/**
 * This is the Application class used in this project.
 */
internal class AppController : MultiDexApplication() {

    companion object {

        private var mContext: AppController? = null

        fun getContext(): AppController {

            if (mContext == null)
                mContext = AppController()

            return mContext as AppController
        }
    }

    override fun onCreate() {
        super.onCreate()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}