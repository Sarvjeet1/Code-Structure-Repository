package com.example.mvvmdemo.utils.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.example.mvvmdemo.utils.constants.ApiConst
import com.example.mvvmdemo.utils.helpers.*
import com.example.mvvmdemo.utils.listeners.ApiListener

class AppContentService : IntentService("AppContentService"), ApiListener {
    private val TAG: String = javaClass.simpleName

    private var beforeApiElapsedTime: Long? = null

    override fun onCreate() {
        super.onCreate()
        LogHelper.debug(tag = TAG, msg = "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogHelper.debug(tag = TAG, msg = "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        LogHelper.debug(tag = TAG, msg = "onBind")
        // We don't provide binding, so return null
        return null
    }

    override fun onHandleIntent(intent: Intent?) {
        LogHelper.debug(tag = TAG, msg = "onHandleIntent")
        fetchAppContentData()
    }

    private fun fetchAppContentData() {
        callFetchAppContentApi()
    }

    /**
     * Here connecting to server and uses App content API
     */
    private fun callFetchAppContentApi() {
//        ApiHelper.hitApiToFetchAppContent(
//            context = this,
//            requestCode = ApiConst.REQUEST_CODE.FETCH_APP_CONTENT,
//            listener = this
//        )
    }

    /**
     * It is called when successful response or data retrieved from AppContent API.
     *
     * @param data
     * @param msg Success message
     */
    private fun onFetchAppContentSuccess(data: Any?, msg: String?) {
//        val appContentModel: AppContentModel? =
//            GsonHelper.convertJsonStringToJavaObject(
//                data!!,
//                AppContentModel::class.java
//            ) as AppContentModel?
//
//        appContentModel?.apply {
//            SharedPreferencesHelper.storeAppContentModel(
//                appContentModel = appContentModel
//            )
//        }

        stopSelf()
    }

    /**
     * It is called when error occurred in getting successful response or data from AppContent API.
     *
     * @param msg Error message
     */
    private fun onFetchAppContentFailure(msg: String?) {
        stopSelf()
    }

    override fun onApiSuccess(requestCode: String, data: Any, message: String?) {
        LogHelper.debug(tag = TAG, msg = "onApiSuccess: requestCode- $requestCode")

        when (requestCode) {
//            ApiConst.REQUEST_CODE.FETCH_APP_CONTENT ->
//                onFetchAppContentSuccess(data = data, msg = message)
        }
    }

    override fun onApiFailure(requestCode: String, errors: Any?, message: String?) {
        LogHelper.debug(tag = TAG, msg = "onApiFailure: requestCode- $requestCode")

        when (requestCode) {
//            ApiConst.REQUEST_CODE.FETCH_APP_CONTENT ->
//                onFetchAppContentFailure(msg = message)
        }
    }

    override fun onApiForceLogout(requestCode: String, message: String?) {
        LogHelper.debug(tag = TAG, msg = "onApiForceLogout: requestCode- $requestCode")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        LogHelper.debug(tag = TAG, msg = "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        LogHelper.debug(tag = TAG, msg = "onRebind")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogHelper.debug(tag = TAG, msg = "onDestroy")
    }

}